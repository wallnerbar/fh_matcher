package swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
 
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.QuestionModel;

@Repository
@Transactional
public class QuestionDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<QuestionModel> getQuestions() {
 
		TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
				"select q from QuestionModel q", QuestionModel.class);
		List<QuestionModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
 
	public QuestionModel getQuestion(String description) {
		try {
			TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
					"select q from QuestionModel q where q.description = :description",
					QuestionModel.class);
			typedQuery.setParameter("description", description);
			QuestionModel question = typedQuery.getSingleResult();
			return question;
		} catch (Exception ex) {
			return null;
		}
 
	}
}


