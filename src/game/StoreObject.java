package game;

public class StoreObject<T extends Number> {
	T current, max;

	public StoreObject(T value, T theMax)
	{
		setMax(theMax);
		setCurrent(value);
	}
	
	public StoreObject(T value)
	{
		this(value, value);
	}

	public T getCurrent() {
		return current;
	}

	public void setCurrent(T current) {
		this.current = current;
	}

	public T getMax() {
		return max;
	}

	public void setMax(T max) {
		this.max = max;
	}
	
	public double getFraction()
	{
		return getCurrent().doubleValue()/getMax().doubleValue();
	}
}