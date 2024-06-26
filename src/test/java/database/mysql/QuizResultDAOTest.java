package database.mysql;

import model.QuizResult;
import model.QuizResultDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 26 June Wednesday 2024 - 10:06
 */
class QuizResultDAOTest {
    private static final DBAccess dbAccess = Main.getdBaccess();
    private final UserDAO userDAO = new UserDAO(dbAccess);
    private static final QuizDAO quizDAO = new QuizDAO(dbAccess);
    private static final QuizResultDAO quizResultDAO = new QuizResultDAO(dbAccess);



    @Test
    @DisplayName("Test QuizResultDAO storeOne and getStudentResultsByQuizId")
    void storeOneAndgetStudentResults() {
        int defaultResultsSize = quizResultDAO.getAllResults().size();
        int defaultResultsByQuizSize = quizResultDAO.getStudentResultsByQuizId(1,1).size();
        int defaultResultsByStudentSize = quizResultDAO.getResultsByStudent(1).size();
        QuizResult quizResult = new QuizResult(0,userDAO.getById(1),quizDAO.getById(1),12);
        quizResultDAO.storeOne(quizResult);
        int resultsByQuizSize = quizResultDAO.getStudentResultsByQuizId(1,1).size();
        int resultsByStudentSize = quizResultDAO.getStudentResultsByQuizId(1,1).size();
        int resultsSize = quizResultDAO.getAllResults().size();
        assertEquals(defaultResultsByQuizSize+1,resultsByQuizSize);
        assertEquals(defaultResultsByStudentSize+1,resultsByStudentSize);
        assertEquals(defaultResultsSize+1,resultsSize);

    };

    @Test
    @DisplayName("Test convertQuizResultDTOToQuizResult")
    void convertQuizResultDTOToQuizResult() {
        QuizResultDTO quizResultDTO = new QuizResultDTO(1,1,1,12);
        QuizResult quizResult = quizResultDAO.convertQuizResultDTOToQuizResult(quizResultDTO);
        assertEquals(quizResultDTO.getQuizId(),quizResult.getQuiz().getQuizId());
        assertEquals(quizResultDTO.getStudentId(),quizResult.getStudent().getUserId());
        assertEquals(quizResultDTO.getScore(),quizResult.getScore());
        assertEquals(quizResultDTO.getResultId(),quizResult.getResultId());

    }; @Test
    @DisplayName("Test convertQuizResultToQuizResultDTO")
    void convertQuizResultToQuizResultDTO() {
        QuizResult quizResult = new QuizResult(1,userDAO.getById(1),quizDAO.getById(1),12);
        QuizResultDTO quizResultDTO = quizResultDAO.convertQuizResultToQuizResultDTO(quizResult);
        assertEquals(quizResult.getQuiz().getQuizId(),quizResultDTO.getQuizId());
        assertEquals(quizResult.getStudent().getUserId(),quizResultDTO.getStudentId());
        assertEquals(quizResult.getScore(),quizResultDTO.getScore());
        assertEquals(quizResult.getResultId(),quizResultDTO.getResultId());

    };



}