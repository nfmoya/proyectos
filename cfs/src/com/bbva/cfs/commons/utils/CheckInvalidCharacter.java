package com.bbva.cfs.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

public class CheckInvalidCharacter {

	public static void checkMap(Object parameters, String chracterToSearch, String chracterToReplace) {
		Map parametersMap = (Map) parameters;
		
		validateMap(parametersMap,chracterToSearch,chracterToReplace);
	}
	public static Object reCheckMap(Object parameters, String chracterToSearch, String chracterToReplace){

		Map<String, Comparable> map = (HashMap<String, Comparable>) parameters;
		Map<String, Comparable> mapSend = map;
		// Map -> Set -> Iterator -> Map.Entry -> troublesome
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			if(  mapEntry.getKey().toString().equalsIgnoreCase("cursor") 
			||   mapEntry.getKey().toString().equalsIgnoreCase("po_d_error")	
			||   mapEntry.getKey().toString().equalsIgnoreCase("po_c_error")		){
				System.out.println("no se revisa:"+ mapEntry.getKey().toString());
				
				
				
			}else{
				if(mapEntry.getValue() == null){
					System.out.println("The key is: " + mapEntry.getKey()
							+ ",value is :" + mapEntry.getValue() + ", new value is : NULL");
//					mapSend.put( (String) mapEntry.getKey(), map.get(mapEntry.getKey()));
				}else{
					System.out.println("The key is: " + mapEntry.getKey()
							+ ",value is :" + mapEntry.getValue() + ", new value is :"+mapEntry.getValue().toString().replaceAll(chracterToSearch,chracterToReplace) );
					if(mapEntry.getValue().toString().contains("Ñ")){
						mapSend.put( (String) mapEntry.getKey(), mapEntry.getValue().toString().replaceAll(chracterToSearch,chracterToReplace));
						System.out.println("PUTY");
					}
//					else
				}
			}
		}
		
		return (Object) mapSend;
	}
	private static void validateMap(Map parametersMap, String chracterToSearch,
			String chracterToReplace) {
		Iterator mapIterator = parametersMap.keySet().iterator();
		while (mapIterator.hasNext()){
			Object key = parametersMap.get(mapIterator.next());
			try {
				String keyString = (String) key;
				if (keyString!=null) {
					if (contains(keyString, chracterToSearch)) {
						String keyModify = checkFieldValueToReplaceChracter(
								keyString, chracterToSearch, chracterToReplace);
						parametersMap.put(key.toString(),keyModify);
					}
				}
			} catch (ClassCastException e) {

			}
		}
	}

	private static String checkFieldValueToReplaceChracter(Object object,
			String chracterToSearch, String chracterToReplace) {
		String value = (String) object;

		value = value.replaceAll(chracterToSearch, chracterToReplace);

		return value;
	}

	private static boolean contains(String value, String chracter) {
		int valueOfContains = value.indexOf(chracter);
		return valueOfContains!=-1;
	}

	public static void checkMapCursor(Object parameters, String chracterToSearch,
			String chracterToReplace) {
		
		Map parametersMap = (Map) parameters;
		List arrayToTest;
		
		
		if (isNotNull(parametersMap.get("cursor"))) {
			arrayToTest = (ArrayList) parametersMap.get(DatabaseConstants.PO_CURSOR);
		}else{
			return;
		}
		
		for (int i = 0 ; i < arrayToTest.size() ; i++){
			DynaBean parametersCursor = (DynaBean) arrayToTest.get(i);
			DynaProperty[] dynaPropertyArray = parametersCursor.getDynaClass().getDynaProperties();
			
			for (int countArray = 0 ; countArray < dynaPropertyArray.length ; countArray++) {
				DynaProperty dynaProperty = dynaPropertyArray[countArray];
				if(checkObjectCointainsInvalidCharacter(parametersCursor,dynaProperty,chracterToSearch,chracterToReplace)){
					modifyParameterValue(parametersCursor,dynaProperty,chracterToSearch,chracterToReplace);
				}
			}
			
		}
		
	}

	private static void modifyParameterValue(DynaBean parametersCursor,
			DynaProperty dynaProperty, String chracterToSearch,
			String chracterToReplace) {
		String keyModify = checkFieldValueToReplaceChracter(parametersCursor.get(dynaProperty.getName()), chracterToSearch, chracterToReplace);
		parametersCursor.set(dynaProperty.getName(), keyModify);
	}

	private static boolean checkObjectCointainsInvalidCharacter(
			DynaBean parametersCursor, DynaProperty dynaProperty, String chracterToSearch
			, String chracterToReplace) {
		
		boolean containsInvalidCharacter = false;
		
		String clazz = dynaProperty.getType().toString();
		if (contains(clazz,"String") && (parametersCursor.get(dynaProperty.getName()) != null)){
			String valueOfKey = String.valueOf(parametersCursor.get(dynaProperty.getName()));
			if (contains(valueOfKey, chracterToSearch)
					&& (!valueOfKey.equalsIgnoreCase(chracterToSearch))){
				containsInvalidCharacter = true;
			}
		}
		
		return containsInvalidCharacter;
	}

	private static boolean isNotNull(Object object) {
		return object != null;
	}
}
