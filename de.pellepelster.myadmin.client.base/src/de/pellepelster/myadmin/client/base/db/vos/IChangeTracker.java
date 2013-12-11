package de.pellepelster.myadmin.client.base.db.vos;

public interface IChangeTracker
{
	boolean hasChanges();

	void clearChanges();

	void copyChanges(IChangeTracker sourceChangeTracker);

}
