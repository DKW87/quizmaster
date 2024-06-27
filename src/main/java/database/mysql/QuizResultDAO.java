package database.mysql;

import javafx.scene.control.Alert;
import model.QuizResult;
import model.QuizResultDTO;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static utils.Util.showAlert;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Haziran Çarşamba 2024 - 16:42
 */
public class QuizResultDAO extends AbstractDAO {


    private final QuizDAO quizDao = new QuizDAO(dbAccess);
    private final UserDAO userDao = new UserDAO(dbAccess);


    public QuizResultDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public List<QuizResult> getAllResults() {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result   order by date DESC;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                var quizResult = createQuizResultFromResultSet(resultSet);
                quizResultList.add(quizResult);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizResultList;
    }


    /**
     * Retrieves a list of QuizResults for a specific student based on the provided studentId.
     *
     * @param studentId the unique identifier of the student
     * @return a list of QuizResult objects associated with the student
     */
    public List<QuizResult> getResultsByStudent(int studentId) {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result WHERE userId = ? order by date DESC;";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                var quizResult = createQuizResultFromResultSet(resultSet);
                quizResultList.add(quizResult);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizResultList;
    }

    /**
     * Retrieves a list of QuizResult objects based on the given quiz ID and student ID.
     *
     * @param  quizId   the ID of the quiz
     * @param  studentId the ID of the student
     * @return           a list of QuizResult objects
     */
    public List<QuizResult> getStudentResultsByQuizId(int quizId, int studentId) {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result WHERE quizId = ? AND userId = ? order by date;";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, quizId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                var quizResult = createQuizResultFromResultSet(resultSet);
                quizResultList.add(quizResult);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizResultList;
    }


    /**
     * Stores a single QuizResult object in the Result table of the database.
     *
     * @param  quizResult  the QuizResult object to be stored
     * @return                 the primary key of the inserted row, or 0 if an error occurred
     */
    public int storeOne(QuizResult quizResult) {
        String sql = "INSERT INTO  Result (date ,userId, quizId, score) VALUES (?, ?, ?, ?)";
        int primaryKey = 0;
        try {
            setupPreparedStatementWithKey(sql);
            setQuizResultToQuery(quizResult); //setQuizResultToQuery
            primaryKey = this.executeInsertStatementWithKey();
            quizResult.setResultId(primaryKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primaryKey;

    }

    public void exportResultAdminExport() {
        String sqlViewExport = "SELECT * FROM RESULTADMINEXPORT";
        try (FileWriter writer = new FileWriter("quiz_results " + LocalDate.now() + ".txt")) {
            this.setupPreparedStatement(sqlViewExport);
            ResultSet resultSet = executeSelectStatement();

            String[] headers = {"User ID", "UserName", "Quiz ID", "Quiz Naam", "Score", "Gehaald"};
            String formatString = "%-10s| %-13s| %-10s| %-30s| %-10s| %-10s\n";

            writer.write(String.format(formatString, (Object[]) headers));
            writer.write("--------------------------------------------------------------------------------------------\n");
             writeResultSetToFile(resultSet, writer, formatString); // Call the helper method
            //saveToFile(writer2);
            showAlert(Alert.AlertType.INFORMATION, "Succes", "Quiz Results succesvol geexporteerd");

        } catch (SQLException | IOException e) { // Combined catch for both exceptions
            System.err.println("Error in exportResultAdminExport: " + e.getMessage());
        }
    }

    // Helper method for the resultSet in exports from SQL view.
    private FileWriter writeResultSetToFile(ResultSet resultSet, FileWriter writer, String formatString) throws SQLException, IOException {
        while (resultSet.next()) {
            Object[] rowData = {
                    resultSet.getInt("UserId"),
                    resultSet.getString("UserName"),
                    resultSet.getInt("quizId"),
                    resultSet.getString("QuizNaam"),
                    resultSet.getInt("Score"),
                    resultSet.getInt("Gehaald")
            };
            writer.write(String.format(formatString, rowData));
        }
        return writer;
    }

    // Method for the File Saver.
    private void saveToFile(FileWriter fileWriter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Quiz Results");
//        fileChooser.setSelectedFile(new File("quiz_results.txt"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter newWriter = new FileWriter(fileToSave)) {
                newWriter.write(fileWriter.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Error exporting results: " + e.getMessage(),
                        "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public QuizResult convertQuizResultDTOToQuizResult(QuizResultDTO quizResultDTO) {
        return new QuizResult(quizResultDTO.getResultId(),
                userDao.getById(quizResultDTO.getStudentId()),
                quizDao.getById(quizResultDTO.getQuizId()),
                quizResultDTO.getScore(),
                quizResultDTO.getDate()
        );
    }
    /**
     * Converts a QuizResult object to a QuizResultDTO object.
     *
     * @param  qr  the QuizResult object to be converted
     * @return     the converted QuizResultDTO object
     */
    public QuizResultDTO convertQuizResultToQuizResultDTO(QuizResult qr) {
        return new QuizResultDTO(qr.getResultId(),
                qr.getStudent().getUserId(),
                qr.getQuiz().getQuizId(),
                qr.getScore()
        );
    }

    /**
     * Creates a QuizResult object from a ResultSet.
     *
     * @param  resultSet  the ResultSet containing the data to create the QuizResult object
     * @return            the created QuizResult object
     * @throws SQLException if there is an error retrieving data from the ResultSet
     */
    private QuizResult createQuizResultFromResultSet(ResultSet resultSet) throws SQLException {
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
        int userId = resultSet.getInt("userId");
        int resultId = resultSet.getInt("resultId");
        int quizId = resultSet.getInt("quizId");
        int score = resultSet.getInt("score");
        var user = userDao.getById(userId);
        var quiz = quizDao.getById(quizId);
        return new QuizResult(resultId, user, quiz, score, date);
    }
    /**
     * Sets the values of a PreparedStatement object with the data from a QuizResult object.
     *
     * @param  quizResult  the QuizResult object containing the data to be set
     * @throws SQLException if there is an error setting the values in the PreparedStatement
     */
    private void setQuizResultToQuery(QuizResult quizResult) throws SQLException {
        preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(quizResult.getDate()));
        preparedStatement.setInt(2, quizResult.getStudent().getUserId());
        preparedStatement.setInt(3, quizResult.getQuiz().getQuizId());
        preparedStatement.setInt(4, quizResult.getScore());

    }


}
