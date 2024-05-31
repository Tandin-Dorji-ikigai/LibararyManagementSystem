package composite;

/**
 * 
 * This is an interface that defines the operations that can be performed on a
 * 
 * component in a composite structure.
 * 
 * The `Component` interface defines four methods:
 * 
 * - `add(Component component)`: This method is used to add a child component to
 * 
 * the current component.
 * 
 * - `remove(Component component)`: This method is used to remove a child
 * component
 * 
 * from the current component.
 * 
 * - `getChild(int index)`: This method is used to retrieve a child component by
 * 
 * its index in the list of child components.
 * 
 * - `display()`: This method is used to display the current component.
 * 
 * The `Component` interface is implemented by both the `Category` and `Product`
 * 
 * classes, which allows for uniform handling of both types of objects in a
 * 
 * composite structure.
 * 
 */
public interface Component {
    void add(Component component);

    void remove(Component component);

    Component getChild(int index);

    void display();
}