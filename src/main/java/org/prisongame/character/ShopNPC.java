package org.prisongame.character;

import org.prisongame.commands.Command;
import org.prisongame.game.Game;
import org.prisongame.items.Item;
import org.prisongame.map.Location;

import java.util.List;

public class ShopNPC extends NPC implements Processer {

    String dialogLine;
    String noItemsMessage = "";
    boolean removeOnPurchase = false;

    public ShopNPC(String name, Location location, List<Item> shopItems,  String dialogLine, String noItemsMessage) {
        super(name, location, shopItems);
        this.dialogLine = dialogLine;
        this.noItemsMessage = noItemsMessage;
        this.removeOnPurchase = true;
    }

    public ShopNPC(String name, Location location, List<Item> shopItems, String dialogLine) {
        super(name, location, shopItems);
        this.dialogLine = dialogLine;
    }

    @Override
    public Processer processCommand(Command command) {
         if (command.getCommandWord().equals("nevermind") || command.getCommandWord().equals("no")) {
             Game.getOutput().println("Ok bye then");
             return null;
         }
        Item item = null;
         if (getInventoryList().size() == 1) {
             if (command.getCommandWord().equals("yes")) {
                 item = getInventoryList().getFirst();
             }
         } else {
             item = getInventory().checkObjectAvailable(command.getCommandWord());
         }
         if (item != null) {
             if (Game.getPlayer().spendMoney(item.getValue())) {
                 Game.getPlayer().addToInventory(item);
                 Game.getOutput().println("You bought " + item.getName() + "!");
                 if (removeOnPurchase) {
                     removeFromInventory(item);
                 }
             } else {
                 Game.getOutput().println("You don't have enough money for that!");
             }
             return null;
         }
         Game.getOutput().println("Enter an option or say 'nevermind'");
         return this;
    }

    @Override
    public void talk() {
        if (getInventoryList().isEmpty()) {
            Game.getOutput().println(noItemsMessage);
        }
        Game.setSubProcess(this);
        StringBuilder builder = new StringBuilder();
        builder.append(dialogLine);
        if(getInventoryList().size() == 1) {
             builder.append("\n")
                     .append("Buy ")
                     .append(getInventoryList().getFirst().getName())
                     .append(" - ")
                     .append(getInventoryList().getFirst().getValue())
                     .append("$ ?");
        } else {
            for(Item item : getInventoryList()) {
                builder.append("\n" + item.getName() + " - " + "$" + item.getValue());
            }
        }
        say(builder.toString());
    }
}
