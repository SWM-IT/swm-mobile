package de.swm.gwt.client;

import de.swm.gwt.client.pk.IPrimaryKey;

import java.io.Serializable;

/**
 * Abstracter PK, der Gemeinsamkeiten der IPrimaryKey's anbietet.
 *
 * @author Steiner.Christian<br>
 *         copyright 2014 SWM Service GmbH
 */
public abstract class AbstractPk implements IPrimaryKey, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Schluessel fuer neue Objekte. *
	 */
	protected static final int NEW_KEY = -1;

	private static final int SHIFT = 32;

	private long innerPk;

	private boolean generatedOnClientSide = false;

	private String correlationID;


	/**
	 * Constructor for GWt Serialization.
	 *
	 * @param innerPk       der innere DB-Key
	 * @param correlationID ID fuer Web-Conversations (mehrstufige Prozesse)
	 */
	protected AbstractPk(long innerPk, String correlationID) {
		this.innerPk = innerPk;
		this.correlationID = correlationID;
	}


	/**
	 * Returns the innerPk.
	 *
	 * @return the innerPk
	 */
	@Override
	public long getInnerPk() {
		return innerPk;
	}

	/**
	 * InnerPk the innerPk to set.
	 *
	 * @param innerPk the innerPk to set
	 */
	public void setInnerPk(long innerPk) {
		this.innerPk = innerPk;
	}

	/**
	 * Makiert, dass der primary key auf der Clientseite generiert wurde.
	 *
	 * @return true, wenn auf der Clientseite generiert
	 */
	public boolean isGeneratedOnClientSide() {
		return generatedOnClientSide;
	}

	/**
	 * Makiert, dass der primary key auf der Clientseite generiert wurde.
	 *
	 * @param generatedOnClientSide true, wenn auf der Clientseite generiert.
	 */
	public void setGeneratedOnClientSide(boolean generatedOnClientSide) {
		this.generatedOnClientSide = generatedOnClientSide;
	}


	/**
	 * True wenn neu (nicht in der DB).
	 *
	 * @return true wenn neu (nicht in der DB)
	 */
	@Override
	public boolean isNew() {
		return this.innerPk == NEW_KEY;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see de.swm.gwt.client.pk.IPrimaryKey#getCorrelationID()
	 */
	@Override
	public String getCorrelationID() {
		return this.correlationID;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see de.swm.gwt.client.pk.IPrimaryKey#hasCorrelationID()
	 */
	@Override
	public boolean hasCorrelationID() {
		return this.correlationID != null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see de.swm.gwt.client.pk.IPrimaryKey#setCorrelationID(java.lang.String)
	 */
	@Override
	public void setCorrelationID(String id) {
		this.correlationID = id;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (!isNew()) {
			result = prime * result + (int) (innerPk ^ (innerPk >>> SHIFT));
		} else {
			result = prime * result
					+ ((correlationID == null) ? 0 : correlationID.hashCode());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractPk other = (AbstractPk) obj;
		if (!isNew()) {
			// ist in der DB
			if (innerPk != other.innerPk) {
				return false;
			}
			return true;
		} else {
			if (this.correlationID == null && other.correlationID == null) {
				return true;
			}
			if (this.correlationID != null && other.correlationID == null) {
				return false;
			} else if (this.correlationID == null
					&& other.correlationID != null) {
				return false;
			}
			// nicht in der DB
			if (!correlationID.equals(other.correlationID)) {
				return false;
			}

			return true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" [innerPk=");
		builder.append(innerPk);
		builder.append(", ");
		if (correlationID != null) {
			builder.append("correlationID=");
			builder.append(correlationID);
		}
		builder.append("]");
		return builder.toString();
	}
}
