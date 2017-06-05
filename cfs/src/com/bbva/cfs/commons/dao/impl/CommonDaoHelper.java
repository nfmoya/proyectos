package com.bbva.cfs.commons.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import ar.com.itrsa.sam.IContext;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DatabaseConstants;

public abstract class CommonDaoHelper {

	protected IContext samContext;

	protected Result result;

	protected Map parameters;

	protected void initialise() {
		this.result = new Result();
		this.parameters = new HashMap();
	}

	protected abstract IContext getSamContext();

	protected Iterator getIterFromCursor() {
		return getListFromCursor().iterator();
	}

	protected List getListFromCursor() {
		ArrayList dynaListResult = (ArrayList) this.parameters
				.get(DatabaseConstants.PO_CURSOR);

		return CollectionUtils.isNotEmpty(dynaListResult) ? dynaListResult
				: new ArrayList();
	}
}
