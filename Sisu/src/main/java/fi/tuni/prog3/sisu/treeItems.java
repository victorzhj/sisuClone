package fi.tuni.prog3.sisu;

/**
 * Class to represent an item in a treeview.
 */
public class treeItems {
    treeItems(String name, String id, Boolean isLeaf,Boolean HasNoStudyModules, Boolean courseSubModules){
        this.name = name;
        this.id = id;
        this.isLeaf = isLeaf;
        this.hasNoStudyModules = HasNoStudyModules;
        this.courseSubModules = courseSubModules;
    }

    treeItems(String name, String id, Boolean isLeaf,Boolean HasNoStudyModules, Boolean courseSubModules, ModuleData thisModule){
        this.name = name;
        this.id = id;
        this.isLeaf = isLeaf;
        this.hasNoStudyModules = HasNoStudyModules;
        this.courseSubModules = courseSubModules;
        this.thisModule = thisModule;
    }
    private String name;
    private String id;
    private Boolean isLeaf;
    private Boolean hasNoStudyModules;
    private Boolean courseSubModules;
    private ModuleData thisModule;
    /**
     * Returns the groupId of the item 
     * @return String group of the item
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the name of the item
     * @return String name of the item
     */
    public String getName() {
        return name;
    }
    /**
     * Returns true if the item has no children or false if it does
     * @return Boolean  True if is leaf, False if is not leaf
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }
    /**
     * Returns True if the item is studyModule False if it isn't
     * @return Boolean True if is studyModule, False if isn't
     */
    public Boolean gethasNoStudyModules() {
        return hasNoStudyModules;
    }
    /**
     * Returns True if items submodules are courses. Otherwise False.
     * @return Boolean True if items submodules are courses. Otherwise False.
     */
    public Boolean getCourseSubModules() {
        return courseSubModules;
    }
    /**
     * Returns this TreeItems module. If there are any.
     * @return Returns this TreeItems module. If there are any.
     */
    public ModuleData getThisModule() {
        return this.thisModule;
    }
    @Override
    /**
     * Gets the name of the item
     */
    public String toString() {
        return getName();
    }
}
