import java.util.ArrayList;

public class Pile<T> {
    private ArrayList<T> elements;

    public Pile() {
        elements = new ArrayList<>();
    }

    public void push(T element) {
        elements.add(element);
    }

    public T pop() {
        if (elements.isEmpty()) {
            return null; 
        }
        return elements.remove(elements.size() - 1);
    }

    public T peek() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public ArrayList<T> getElements() {
        return new ArrayList<>(elements);
    }
    public void afficherPile() {
        System.out.println("Éléments de la pile : " + this.getElements());
    }
}
