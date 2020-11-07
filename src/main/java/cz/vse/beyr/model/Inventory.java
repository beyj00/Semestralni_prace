
package cz.vse.beyr.model;
import java.util.*;

/**
 * Tato třída představuje logiku vytvoření inventáře a chování inventáře během hry
 */
    public class Inventory {
        private Map<String, Item> inventoryMap;
        private GamePlan gamePlan;

        /**
         * Konstruktor třídy.
         * 
         * @param gamePlan herní plán
         */
        
        public Inventory(GamePlan gamePlan){
            this.inventoryMap = new HashMap<String, Item>();
            this.gamePlan = gamePlan;
        }
        
        /**
         * Přidá item do inventáře a kontroluje, jeslti se tam vejde.
         * @param name název předmětu
         * @param item předmět
         * @return vraci false když je inventář plný a true, pokud je do inventáře přidán
         */
        public boolean addToInventory(String name, Item item){
            if(this.inventoryMap.size() < 2){
                this.inventoryMap.put(name, item);
                return true;
            }
            return false;
        }
        /**
         * Zjišťujeme, jestli je daný předmět v inventáři a jestli je možné ho vymazat a následně přidat do aktuální oblasti
         * 
         * @param itemName název předmětu
         * @return vrací, jestli je předmět v inventáři, nebo jestli byl vyndán
         */
        public String removeItem(String itemName)
        {     
            boolean itemExist = false;
        
            for(Item item: this.inventoryMap.values()){ //prochazi mapu inventoryMap a ta kazdy cyklus vrati item
                if(item.getName().equals(itemName)){ //kontrola, zda-li jmeno  itemu se rovna itemu, ktery chceme smazat z inventare
                    gamePlan.getCurrentArea().addItem(item); //vrati inventar do current area, ktere ziskame z game planu
                    itemExist = true;
                }
            }
        
            if (itemExist) {
                inventoryMap.remove(itemName); //vymaze dany item z inventoryMap
                return "Vyndal jsi předmět " + itemName + " z inventáře";
            }
            return "Tento předmět nemáš v inventáři";
        }
    
        /**
         *  Metoda ověřuje, zda li je daný předmět v inventáři.
         *  Cyklus prochází mapu inventoryMap a při každém cyklu vrátí item z mapy.
         *  Ověřuje se, zda-li zadaný item je v inventáři nebo ne.
         *  
         *  @param itemName název předmětu
         *  @return vrací, jestli je předmět v inventáři
         */
        public boolean isInInventory(String itemName)
        {
            for(Item item: this.inventoryMap.values()){ //prochazi mapu inventoryMap a ta kazdy cyklus vrati item
                if(item.getName().equals(itemName)){ //kontrola, zda-li jmeno  itemu se rovna itemu, ktery chceme z inventare
                    return true;
                }
            }
            return false;
        }
        
        public boolean isWeaponInInventory()
        {
            for(Item item: this.inventoryMap.values()){ //prochazi mapu inventoryMap a ta kazdy cyklus vrati item
                if(item.getWeapon() == true){ //kontrola, zda-li jmeno  itemu se rovna itemu, ktery chceme z inventare
                    return true;
                }
            }
            return false;
        }
        
        /**
         * @return inventář
         */
        public Map<String, Item> getInventory()
        {
            return this.inventoryMap;
        }
        
        /**
         * @param itemName název předmětu
         * @return název předmětu v inventáři
         */
        public Item getItem(String itemName)
        {
            return inventoryMap.get(itemName);
        }
    
}
