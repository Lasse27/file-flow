package control.procedure;


import control.procedure.handler.*;
import exception.ProcedureDispatcherException;
import model.procedure.Procedure;
import model.procedure.ProcedureType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProcedureDispatcherTest
{
	private static final String TEST_PROCEDURE_ID = "1";


	private static final String TEST_PROCEDURE_NAME = "TestProcedure";


	private static final String MSG_ON_NULL_HANDLER = "Handler should not be null for a valid procedure type";


	private static final String MSG_ON_WRONG_HANDLER = "Handler should be of type %s for procedure";


	private static final String MSG_ON_MISSING_THROW = "Expected ProcedureDispatcherException to be thrown for an unsupported procedure type";

	// TODO: Test dispatch() method



	@Test
	@DisplayName ("Test getHandlerForProcedure() with an invalid procedure type")
	void testGetHandlerForProcedure_InvalidProcedureType ()
	{
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(null).build();

		assertThrows(ProcedureDispatcherException.class, () -> dispatcher.getHandlerForProcedure(procedure), MSG_ON_MISSING_THROW);
	}



	@Test
	@DisplayName ("Test getHandlerForProcedure() with a valid MOVE procedure type")
	void testGetHandlerForProcedure_ValidMoveProcedureHandler ()
	{
		// PROCESS
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(ProcedureType.MOVE).build();

		final ProcedureHandler handler = dispatcher.getHandlerForProcedure(procedure);

		// ASSERT
		assertNotNull(handler, MSG_ON_NULL_HANDLER);
		assertInstanceOf(MoveProcedureHandler.class, handler, String.format(MSG_ON_WRONG_HANDLER, MoveProcedureHandler.class.getSimpleName()));
	}



	@Test
	@DisplayName ("Test getHandlerForProcedure() with a valid CLEAN procedure type")
	void testGetHandlerForProcedure_ValidCleanProcedureHandler ()
	{
		// PROCESS
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(ProcedureType.CLEAN).build();

		final ProcedureHandler handler = dispatcher.getHandlerForProcedure(procedure);

		// ASSERT
		assertNotNull(handler, MSG_ON_NULL_HANDLER);
		assertInstanceOf(CleanProcedureHandler.class, handler, String.format(MSG_ON_WRONG_HANDLER, CleanProcedureHandler.class.getSimpleName()));
	}



	@Test
	@DisplayName ("Test getHandlerForProcedure() with a valid ZIP procedure type")
	void testGetHandlerForProcedure_ValidZipProcedureHandler ()
	{
		// PROCESS
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(ProcedureType.ZIP).build();

		final ProcedureHandler handler = dispatcher.getHandlerForProcedure(procedure);

		// ASSERT
		assertNotNull(handler, MSG_ON_NULL_HANDLER);
		assertInstanceOf(ZipProcedureHandler.class, handler, String.format(MSG_ON_WRONG_HANDLER, ZipProcedureHandler.class.getSimpleName()));
	}



	@Test
	@DisplayName ("Test getHandlerForProcedure() with a valid UNZIP procedure type")
	void testGetHandlerForProcedure_ValidUnzipProcedureHandler ()
	{
		// PROCESS
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(ProcedureType.UNZIP).build();

		final ProcedureHandler handler = dispatcher.getHandlerForProcedure(procedure);

		// ASSERT
		assertNotNull(handler, MSG_ON_NULL_HANDLER);
		assertInstanceOf(UnzipProcedureHandler.class, handler, String.format(MSG_ON_WRONG_HANDLER, UnzipProcedureHandler.class.getSimpleName()));
	}



	@Test
	@DisplayName ("Test getHandlerForProcedure() with a valid RENAME procedure type")
	void testGetHandlerForProcedure_ValidRenameProcedureHandler ()
	{
		// PROCESS
		final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
		final Procedure procedure = new Procedure.Builder().withId(TEST_PROCEDURE_ID).withName(TEST_PROCEDURE_NAME).withType(ProcedureType.RENAME).build();

		final ProcedureHandler handler = dispatcher.getHandlerForProcedure(procedure);

		// ASSERT
		assertNotNull(handler, MSG_ON_NULL_HANDLER);
		assertInstanceOf(RenameProcedureHandler.class, handler, String.format(MSG_ON_WRONG_HANDLER, RenameProcedureHandler.class.getSimpleName()));
	}
}