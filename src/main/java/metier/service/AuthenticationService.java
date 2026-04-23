package metier.service;

import dao.IntervenantDao;
import dao.StudentDao;
import metier.modele.Intervenant;
import metier.modele.Student;

import dao.JpaUtil;
public class AuthenticationService {

    public Student loginStudent(String email, String password){
        Student stu = null;
        try{
            StudentDao studentDao = new StudentDao();
            JpaUtil.creerContextePersistance();

            stu = studentDao.findByEmail(email);

            if(stu != null && stu.getPassword().equals(password)){
                //somethign this shyt will do
            }else{
                stu = null;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

        }
        finally {
            JpaUtil.fermerContextePersistance();
        }

        return stu;
    }

    public Intervenant loginIntervenant(String email, String password){
        Intervenant interv = null;
        try{
            IntervenantDao intervenantDao = new IntervenantDao();
            JpaUtil.creerContextePersistance();

            interv = intervenantDao.findByEmail(email);

            if(interv != null && interv.getPassword().equals(password) ){
                //somewthing with interv;
            }else{
                interv = null;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

        }
        finally {
            JpaUtil.fermerContextePersistance();
        }

        return interv;
    }


}
