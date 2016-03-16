package jp.co.withlinkage.sato.s5_4;

public class Pocket<E> {
	private E data;
	public void put(E d){ this.data = d; }
	public E get(){ return this.data; }
}