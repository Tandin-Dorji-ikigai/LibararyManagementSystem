package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a concrete implementation of the `Component` interface that
 * represents
 * a category. The `Category` class contains a list of child components, which
 * can be other
 * categories or products. The `Category` class provides methods to add and
 * remove child components,
 * as well as a method to retrieve a child component by index. The `display()`
 * method is used to
 * display the name of the category and the names of its child components.
 */

public class Category implements Component {
    private String name;
    private List<Component> children;

    public Category(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return children.get(index);
    }

    @Override
    public void display() {
        System.out.println(name);
        for (Component component : children) {
            component.display();
        }
    }

    public Category getCategoryByName(String name) {
        if (this.name.equals(name)) {
            return this;
        }

        for (Component component : children) {
            if (component instanceof Category) {
                Category category = (Category) component;
                Category result = category.getCategoryByName(name);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    // Method to get all category names
    public List<String> getAllCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        getAllCategoryNames(this, categoryNames);
        return categoryNames;
    }

    // Helper method to recursively get all category names
    private void getAllCategoryNames(Category category, List<String> categoryNames) {
        categoryNames.add(category.getName());
        for (Component component : category.children) {
            if (component instanceof Category) {
                getAllCategoryNames((Category) component, categoryNames);
            }
        }
    }

    public String getName() {
        return name;
    }
}
