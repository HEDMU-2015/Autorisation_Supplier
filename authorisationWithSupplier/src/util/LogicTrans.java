package util;
import java.util.function.Consumer;
import java.util.function.Supplier;

import persistence.DataAccess;

public class LogicTrans<T> {
	
	DataAccess dataaccess = null;
	
	public LogicTrans(DataAccess dataccess) {
		this.dataaccess = dataccess;
	}

	public T transaction (Supplier<T> supplier) {
		try {
			T t = supplier.get();
			dataaccess.commit();

			return t;
		} catch (Exception e) {
			dataaccess.rollback();
			throw new RuntimeException("Transaction rolled back", e);
		} finally {
			if(dataaccess != null)
				dataaccess.close();
			
		}
	}

	public void transaction(Consumer<T> consumer, T t) {
		try {
			consumer.accept(t);
			dataaccess.commit();
		} catch (Exception e) {
			dataaccess.rollback();
			throw new RuntimeException("Transaction rolled back", e);
		} finally {
			dataaccess.close();
			
		}
	}
}
