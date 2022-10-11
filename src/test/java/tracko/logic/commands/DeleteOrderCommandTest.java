package tracko.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracko.logic.commands.CommandTestUtil.assertCommandFailure;
import static tracko.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static tracko.logic.commands.CommandTestUtil.showOrderAtIndex;
import static tracko.testutil.TypicalIndexes.INDEX_FIRST;
import static tracko.testutil.TypicalIndexes.INDEX_SECOND;
import static tracko.testutil.TypicalOrders.getTrackOWithTypicalOrders;

import org.junit.jupiter.api.Test;

import tracko.commons.core.Messages;
import tracko.commons.core.index.Index;
import tracko.logic.commands.order.DeleteOrderCommand;
import tracko.model.Model;
import tracko.model.ModelManager;
import tracko.model.UserPrefs;
import tracko.model.order.Order;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteOrderCommandTest {

    private Model model = new ModelManager(getTrackOWithTypicalOrders(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToDelete = model.getOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    //Test for filtered list, currently not implemented
    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    //        showOrderAtIndex(model, INDEX_FIRST_ORDER);
    //
    //        Order orderToDelete = model.getOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
    //        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);
    //
    //        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);
    //
    //        Model expectedModel = new ModelManager(model.getTrackO(), new UserPrefs());
    //        expectedModel.deleteOrder(orderToDelete);
    //        showNoPerson(expectedModel);
    //
    //        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    //    }

    //Test for filtered list, currently not implemented
    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showOrderAtIndex(model, INDEX_FIRST_ORDER);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_ORDER;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackO().getOrderList().size());
    //
    //        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        DeleteOrderCommand deleteFirstCommand = new DeleteOrderCommand(INDEX_FIRST);
        DeleteOrderCommand deleteSecondCommand = new DeleteOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteOrderCommand deleteFirstCommandCopy = new DeleteOrderCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    //    /**
    //     * Updates {@code model}'s filtered list to show no one.
    //     */
    //    private void showNoPerson(Model model) {
    //        model.updateFilteredPersonList(p -> false);
    //
    //        assertTrue(model.getFilteredPersonList().isEmpty());
    //    }
}
