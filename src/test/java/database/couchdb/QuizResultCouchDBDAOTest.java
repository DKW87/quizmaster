package database.couchdb;

import model.QuizResult;
import model.QuizResultDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 26 June Wednesday 2024 - 10:38
 */
class QuizResultCouchDBDAOTest {
    private static final CouchDBaccess couchDBaccess = Main.getCouchDBaccess();
    private static final QuizResultCouchDBDAO quizResultCouchDBDAO = new QuizResultCouchDBDAO(couchDBaccess);

    private static int defaultResultCount;

    @BeforeAll
    static void beforeAll() {
        defaultResultCount = quizResultCouchDBDAO.getAllQuizResults().size();
    }

    @Test
    @DisplayName("Test saveQuizResult in couchDb")
    void saveQuizResult() {
        QuizResultDTO quizResult = new QuizResultDTO(1, 1, 1, 12);
        quizResultCouchDBDAO.saveQuizResult(quizResult);
        int resultCount = quizResultCouchDBDAO.getAllQuizResults().size();
        assertEquals(defaultResultCount + 1, resultCount);
    }

    @Test
    @DisplayName("Test getAllQuizResults  with studentId and quizId")
    void getAllQuizResults() {
        int defaultStudentResultCount = quizResultCouchDBDAO.getAllQuizResults(1, 1).size();
        int defaultStudentQuizResultCount = quizResultCouchDBDAO.getAllQuizResults(1).size();
        QuizResultDTO quizResult = new QuizResultDTO(1, 1, 1, 12);
        quizResultCouchDBDAO.saveQuizResult(quizResult);
        int resultCount = quizResultCouchDBDAO.getAllQuizResults(1, 1).size();
        int resultQuizCount = quizResultCouchDBDAO.getAllQuizResults(1).size();
        assertEquals(defaultStudentResultCount + 1, resultCount);
        assertEquals(defaultStudentQuizResultCount + 1, resultQuizCount);
    }

    @Test
    @DisplayName("Test getQuizResultDocId ")
    void getQuizResultDocId() {
        String doc_Id = "229bc5edaef64862b5192d26585c36f3";
        QuizResult quizResult = quizResultCouchDBDAO.getQuizResultDocId(doc_Id);
        assertNotNull(quizResult);

    }
}

