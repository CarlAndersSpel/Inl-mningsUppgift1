package se.su.inlupp;

public class MyEdge<T> implements Edge<T>{
     //skapa objektet
  private T destination;
  private String name;
  private int weight;

  public MyEdge(T destination, String name, int weight) {
    this.destination = destination;
    this.name = name;
    this.weight = weight;
  }

  public T getDestination() {
    return destination;
  }

  public String getName() {
    return name;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
  }

  @Override
  public String toString() {
    return String.format("%s (%s: %f)", this.destination, this.name, this.weight);
  }
}  
