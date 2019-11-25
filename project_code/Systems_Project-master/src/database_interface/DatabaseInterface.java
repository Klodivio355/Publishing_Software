package database_interface;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.IncompleteInformationException;
import exceptions.InvalidAuthenticationException;
import exceptions.UniqueColumnValueAlreadyExists;
import exceptions.ObjectDoesNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import models.AcceptedArticle;
import models.Authorship;
import models.Edition;
import models.Journal;
import models.JournalEditor;
import models.Submission;
import models.User;
import models.Volume;

public interface DatabaseInterface {

    /**
     * Registers the given user as a base user in the system
     *
     * @param newUser must include email and password
     * @return success or not
     * @throws UserAlreadyExistsException
     * @throws IncompleteInformationException
     * @precondition a user with that email must not exist yet
     */
    public boolean registerBaseUser(User newUser) throws UserAlreadyExistsException, IncompleteInformationException, SQLException;

    /**
     * attempts to change the password of the given user
     *
     * @param user must include email and old password
     * @param newPassword
     * @return success or not
     * @precondition the user exists in the system
     */
    public boolean changePassword(User user, String newPassword) throws UserDoesNotExistException, IncompleteInformationException, InvalidAuthenticationException, SQLException;

    /**
     * Checks whether a given user with that email and password exist
     *
     * @param user must include email and password
     * @return success or not
     * @precondition the user exists in the system
     */
    public boolean validCredentials(User user) throws SQLException, UserDoesNotExistException;

    /**
     * @return a list of all registered journals
     */
    public ArrayList<Journal> getAllJournals() throws SQLException;

    /**
     * @param journal must include ISSN
     * @return a list of all volumes belonging to that journal
     * @throws ObjectDoesNotExistException
     * @precondition the given journal exists
     */
    public ArrayList<Volume> getAllJournalVolumes(Journal journal) throws ObjectDoesNotExistException, SQLException;

    /**
     * @param volume must include volNum and ISSN
     * @return a list of all editions belonging to that volume
     * @throws ObjectDoesNotExistException
     * @precondition the given volume exists
     */
    public ArrayList<Edition> getAllVolumeEditions(Volume volume) throws ObjectDoesNotExistException, SQLException;

    /**
     * @param edition must include volumeNum and editionNum
     * @return a list of all articles in this edition
     * @throws ObjectDoesNotExistException
     * @precondition the given edition exists
     */
    public ArrayList<AcceptedArticle> getallEditionArticles(Edition edition) throws ObjectDoesNotExistException, SQLException;

    /**
     * Creates a new journal and appoints chiefEditor as the main and first editor
     *
     * @param newJournal  must include UNIQUE ISSN and UNIQUE name
     * @param chiefEditor must include ISSN, email and password
     * @return success or not
     * @throws UserDoesNotExistException
     * @throws InvalidAuthenticationException
     * @throws UniqueColumnValueAlreadyExists if the ISSN or Journal name is already
     *                                        in use
     * @precondition the user chiefEditor exists
     */
    public boolean createJournal(Journal newJournal, User chiefEditor)
            throws UserDoesNotExistException, InvalidAuthenticationException, UniqueColumnValueAlreadyExists, SQLException;

    /**
     * Using a chief editor's credentials, appoints a user as an editor for a
     * journal
     *
     * @param newEditor    must include journal.ISSN and email
     * @param journalChief must include email and password
     * @return success or not
     * @throws UserDoesNotExistException
     * @throws InvalidAuthenticationException
     * @precondition the user newEditor must exist and journalChief must exist and
     * be a chief editor of the journal in newEditor
     */
    public boolean promoteUserToEditor(JournalEditor newEditor, JournalEditor journalChief)
            throws UserDoesNotExistException, InvalidAuthenticationException, SQLException, IncompleteInformationException;

    /**
     * Using the credentials of editor, creates a new volume with the next in line
     * volume number for that journal
     *
     * @param journal         must include ISSN
     * @param editor          must include email and password
     * @param publicationYear
     * @return the newlyu created volume
     * @throws ObjectDoesNotExistException
     * @throws InvalidAuthenticationException
     */
    public Volume createNextVolume(Journal journal, JournalEditor editor, int publicationYear)
            throws ObjectDoesNotExistException, InvalidAuthenticationException, SQLException;

    /**
     * Using the credentials of editor, creates a new edition with the next in line
     * edition number for that volume.
     *
     * @param volume           must include volNum and ISSN
     * @param editor           must include email and journal.ISSN
     * @param publicationMonth
     * @return the newly created edition
     * @throws ObjectDoesNotExistException
     * @throws InvalidAuthenticationException
     * @precondition editor and volume must exist
     */
    public Edition createNextEdition(Volume volume, JournalEditor editor, String publicationMonth)
            throws ObjectDoesNotExistException, InvalidAuthenticationException, SQLException;

    /**
     * Using the credentials of author, creates a new submission and appoints thi
     * user as the main author for that submission
     *
     * @param submission must include title, summary and articleContent
     * @param author     must include email and password
     * @return returns the submission object with a value for submissionID of the
     * newly created submission
     * @throws UserDoesNotExistException
     * @throws InvalidAuthenticationException
     * @precondition the user passed exists in the database
     * @postcondition a new submission with the given values has been inserted into
     * the database
     */
    public Submission submitArticle(Submission submission, User author)
            throws UserDoesNotExistException, InvalidAuthenticationException, SQLException, IncompleteInformationException;

    /**
     * Using the credentials of mainAuthor, appoints newAuthor as a co-author to the
     * submission
     *
     * @param submission only needs to include submissionID
     * @param newAuthor  only needs to include email
     * @param mainAuthor only needs to include email and password
     * @return successful or not
     * @throws UserDoesNotExistException
     * @throws ObjectDoesNotExistException
     * @throws InvalidAuthenticationException
     * @precondition all three objects exist in the database
     * @postcondition the newAuthor user has been registered as a co author for that
     * submission
     */
    public boolean addCoAuthor(Submission submission, Authorship newAuthor, Authorship mainAuthor)
            throws UserDoesNotExistException, ObjectDoesNotExistException, InvalidAuthenticationException, SQLException;

}
