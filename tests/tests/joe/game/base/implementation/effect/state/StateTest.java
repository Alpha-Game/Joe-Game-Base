package tests.joe.game.base.implementation.effect.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import joe.classes.identifier.AbstractIdentifier;
import joe.game.base.effect.IStateType;
import joe.game.base.implementation.effect.state.State;
import joe.game.base.implementation.effect.state.StateInternal;
import joe.game.base.implementation.effect.state.StateType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StateTest {
	private static int index = 0;
	private static int dataIndex = 0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IStateType<?> createStateType(Class<?> type, boolean canGet, boolean canSet, boolean canAddInstantEffect) {
		return new StateType(Integer.toString(index++), type, canGet, canSet, canAddInstantEffect, false, false, false);
	}
	
	private static Collection<Object[]> createData(Class<?>[]... allClasses) {
		List<Object[]> values = new LinkedList<Object[]>();
		for (int previousStateCount = 1; previousStateCount < 4; previousStateCount++) {
			for (Class<?>[] classes : allClasses) {
				if (classes.length < 1) {
					values.add(new Object[] { String.format("PreviousStateCount<%d>, NoTypes", previousStateCount), previousStateCount, new LinkedList<IStateType<?>>() });
				} else {
					for (int i = 0; i < (1 << 3); i++) {
						boolean canGet = (i & 0x1) != 0;
						boolean canSet = (i & 0x2) != 0;
						boolean canAddInstantEffect = (i & 0x4) != 0;
						List<IStateType<?>> stateTypes = new LinkedList<IStateType<?>>();
						for(Class<?> eachClass : classes) {
							stateTypes.add(createStateType(eachClass, canGet, canSet, canAddInstantEffect));
						}
						values.add(new Object[] { String.format("PreviousStateCount<%d>, CanGet<%b>, CanSet<%b>, CanEffect<%b>, Type1<%s>", previousStateCount, canGet, canSet, canAddInstantEffect, classes[0].getSimpleName()), previousStateCount, stateTypes });
					}
				}
			}
		}
		values = Arrays.asList(values.toArray(new Object[0][]));
		return values;
	}
	
	@Parameters(name= "{0}_{index}")
    public static Collection<Object[]> data() {
    	return createData(
    			new Class<?>[] { }, 
    			new Class<?>[] { Integer.class },
    			new Class<?>[] { Integer.class, Integer.class },
    			new Class<?>[] { String.class },
    			new Class<?>[] { String.class, String.class }
    		);
    }
    
    @Parameter(0)
    public String fTestName;
    
    @Parameter(1)
    public int fPreviousStateCount;
    
    @Parameter(2)    
    public List<IStateType<?>> fTypes;
    
    private MockState fState;
    
    @Before
    public void setup() {
    	assumeTrue(fPreviousStateCount > 0);
    	assumeTrue(fTypes != null);
    	fState = new MockState("", fPreviousStateCount, fTypes);
    	fState.nextState();
    }
    
    @Test
    public void test_State_getValue_NoValue_Single_ValidByIdentifier() {
    	assumeTrue(fTypes.size() > 0);
    	assertNull(fState.getValue(fTypes.get(0).getIdentifier()));
    }
    
    @Test
    public void test_State_getValue_NoValue_Single_ValidByMappable() {
    	assumeTrue(fTypes.size() > 0);
    	assertNull(fState.getValue(new MockMappable(fTypes.get(0).getIdentifier())));
    }
    
    @Test
    public void test_State_getValue_NoValue_Single_ValidByClass() {
    	assumeTrue(fTypes.size() > 0);
    	assertNull(fState.getValue(new MockObject(fTypes.get(0).getIdentifier())));
    }
    
    @Test
    public void test_State_getValue_NoValue_Single_ValidByStateType() {
    	assumeTrue(fTypes.size() > 0);
    	assertNull(fState.getValue(fTypes.get(0)));
    }

    @Test
    public void test_State_getValue_NonFilledValues() {
    	assumeTrue(fTypes.size() > 0);
    	
    	fState = new MockState("", fPreviousStateCount, fTypes);
    	try {
    		fState.getValue(fTypes.get(0));
    		fail();
    	} catch (IllegalStateException e) {
    		// Success
    	}
    }
    
    @Test
    public void test_State_getValue_NonFilledValues_InvalidType() {
    	fState = new MockState("", fPreviousStateCount, fTypes);
    	try {
    		fState.getValue("-1");
    		fail();
    	} catch (IllegalStateException e) {
    		// Success
    	}
    }
    
    @Test
    public void test_State_setValue_Single_ValidByIdentifier() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	final Object newValue = (fTypes.get(0).canGet() && fTypes.get(0).canSet()) ? setValue : null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), fTypes.get(0).getIdentifier(), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByMappable() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	final Object newValue = (fTypes.get(0).canGet() && fTypes.get(0).canSet()) ? setValue : null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockMappable(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByClass() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	final Object newValue = (fTypes.get(0).canGet() && fTypes.get(0).canSet()) ? setValue : null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockObject(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void test_State_setValue_Single_ValidByStateType() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	final Object newValue = (fTypes.get(0).canGet() && fTypes.get(0).canSet()) ? setValue : null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), (IStateType<Object>)fTypes.get(0), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByIdentifier_InvalidType() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = false;
    	final Object setValue = getInvalidType(fTypes.get(0));
    	final Object newValue = null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), fTypes.get(0).getIdentifier(), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByMappable_InvalidType() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = false;
    	final Object setValue = getInvalidType(fTypes.get(0));
    	final Object newValue = null;    	
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockMappable(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByClass_InvalidType() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = false;
    	final Object setValue = getInvalidType(fTypes.get(0));
    	final Object newValue = null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockObject(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void test_State_setValue_Single_ValidByStateType_InvalidType() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = false;
    	final Object setValue = getInvalidType(fTypes.get(0));
    	final Object newValue = null;
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), (IStateType<Object>)fTypes.get(0), setValue));
    	assertEquals(newValue, fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByIdentifier_SetNull() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), fTypes.get(0).getIdentifier(), setValue));
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), fTypes.get(0).getIdentifier(), (Object)null));
    	assertNull(fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByMappable_SetNull() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockMappable(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockMappable(fTypes.get(0).getIdentifier()), (Object)null));
    	assertNull(fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_setValue_Single_ValidByClass_SetNull() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockObject(fTypes.get(0).getIdentifier()), setValue));
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), new MockObject(fTypes.get(0).getIdentifier()), (Object)null));
    	assertNull(fState.getValue(fTypes.get(0)));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void test_State_setValue_Single_ValidByStateType_SetNull() {
    	assumeTrue(fTypes.size() > 0);
    	final boolean returnValue = fTypes.get(0).canSet();
    	final Object setValue = getValidType(fTypes.get(0));
    	
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), (IStateType<Object>)fTypes.get(0), setValue));
    	assertEquals(returnValue, fState.setValue(new MockMappable("0"), (IStateType<Object>)fTypes.get(0), (Object)null));
    	assertNull(fState.getValue(fTypes.get(0)));
    }
    
    @Test
    public void test_State_getPreviousValue_TooLarge() {
    	assumeTrue(fTypes.size() > 0);
    	assumeTrue(fTypes.get(0).canGet());
    	
    	for (int i = 0; i <= fPreviousStateCount; i++) {
    		fState.nextState();
    	}
    	
    	try {
    		fState.getPreviousValue(fPreviousStateCount, fTypes.get(0));
    		fail();
    	} catch (IndexOutOfBoundsException e) {
    		// Success
    	}
    }
    
    @Test
    public void test_State_getPreviousValue_NonFilledValues_InvalidType() {
    	// TODO throw exception for this case
    	assumeTrue(fPreviousStateCount > 1);
    	
    	try {
    		fState.getPreviousValue(1, "-1");
    		fail();
    	} catch (IllegalStateException e) {
    		// Success
    	}
    }
    
    @Test
    public void test_State_getPreviousValue_NonFilledValues() {
    	// TODO throw exception for this case
    	assumeTrue(fPreviousStateCount > 1);
    	assumeTrue(fTypes.size() > 0);
    	
    	try {
    		fState.getPreviousValue(1, fTypes.get(0));
    		fail();
    	} catch (IllegalStateException e) {
    		// Success
    	}
    }
    
    @Test
    public void test_State_getPreviousValue() {
    	assumeTrue(fTypes.size() > 0);
    	assumeTrue(fTypes.get(0).canGet());
    	assumeTrue(fTypes.get(0).canSet());
    	
    	Object[] list = new Object[fPreviousStateCount+1];
    	list[fPreviousStateCount] = getValidType(fTypes.get(0));
    	assertEquals(true, fState.setValue(new MockMappable("0"), fTypes.get(0), list[fPreviousStateCount]));
    	for (int i = 1; i <= fPreviousStateCount; i++) {
    		fState.nextState();
    		list[fPreviousStateCount - i] = getValidType(fTypes.get(0));
    		assertEquals(true, fState.setValue(new MockMappable("0"), fTypes.get(0), list[fPreviousStateCount - i]));
    	}
    	
    	assertEquals(fState.getValue(fTypes.get(0)), fState.getPreviousValue(0, fTypes.get(0)));
    	for (int i = 0; i < fPreviousStateCount; i++) {
    		assertEquals(list[i], fState.getPreviousValue(i, fTypes.get(0)));
    	}
    }
    
    @Test
    public void test_State_getEffectType_Single_Valid_ByIdentifier() {
    	assumeTrue(fTypes.size() > 0);
    	assertTrue(fTypes.get(0) == fState.getEffectType(fTypes.get(0).getIdentifier()));
    }
    
    @Test
    public void test_State_getEffectType_Single_Valid_ByMappable() {
    	assumeTrue(fTypes.size() > 0);
    	assertTrue(fTypes.get(0) == fState.getEffectType(new MockMappable(fTypes.get(0).getIdentifier())));
    }
    
    @Test
    public void test_State_getEffectType_Single_Valid_ByClass() {
    	assumeTrue(fTypes.size() > 0);
    	assertTrue(fTypes.get(0) == fState.getEffectType(new MockObject(fTypes.get(0).getIdentifier())));
    }
    
    @Test
    public void test_State_getEffectType_Single_Invalid_ByIdentifier() {
    	assertNull(fState.getEffectType("-1"));
    }
    
    @Test
    public void test_State_getEffectType_Single_Invalid_ByMappable() {
    	assertNull(fState.getEffectType(new MockMappable("-1")));
    }
    
    @Test
    public void test_State_getEffectType_Single_Invalid_ByClass() {
    	assertNull(fState.getEffectType(new MockObject("-1")));
    }
    
    @Test
    public void test_State_getEffectType_Single_Invalid_Null() {
    	assertNull(fState.getEffectType((String)null));
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Valid_ByIdentifier() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(fTypes.get(0).getIdentifier(), fTypes.get(1).getIdentifier()).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Valid_ByIdentifier_Duplicate() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(fTypes.get(0).getIdentifier(), fTypes.get(0).getIdentifier()).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Valid_ByMappable() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(new MockMappable(fTypes.get(0).getIdentifier()), new MockMappable(fTypes.get(1).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Valid_ByClass() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(new MockObject(fTypes.get(0).getIdentifier()), new MockObject(fTypes.get(1).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Valid_Mixed() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(new MockMappable(fTypes.get(1).getIdentifier()), new MockObject(fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(1) == result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Invalid_ByIdentifier() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType("-1", fTypes.get(0).getIdentifier()).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Invalid_ByMappable() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(new MockMappable("-1"), new MockMappable(fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Invalid_ByClass() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(new MockObject("-1"), new MockObject(fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_Array_Invalid_Null() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(null, new MockObject(fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Valid_ByIdentifier() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(fTypes.get(0).getIdentifier(), fTypes.get(1).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Valid_ByIdentifier_Duplicate() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(fTypes.get(0).getIdentifier(), fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Valid_ByMappable() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(new MockMappable(fTypes.get(0).getIdentifier()), new MockMappable(fTypes.get(1).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Valid_ByClass() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(new MockObject(fTypes.get(0).getIdentifier()), new MockObject(fTypes.get(1).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(0) == result[0]);
    	assertTrue(fTypes.get(1) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Valid_Mixed() {
    	assumeTrue(fTypes.size() > 1);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(new MockMappable(fTypes.get(1).getIdentifier()), new MockObject(fTypes.get(0).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertTrue(fTypes.get(1) == result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Invalid_ByIdentifier() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList("-1", fTypes.get(0).getIdentifier())).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Invalid_ByMappable() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(new MockMappable("-1"), new MockMappable(fTypes.get(0).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Invalid_ByClass() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(new MockObject("-1"), new MockObject(fTypes.get(0).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }
    
    @Test
    public void test_State_getEffectType_Multiple_List_Invalid_Null() {
    	assumeTrue(fTypes.size() > 0);
    	
    	IStateType<?>[] result = fState.getEffectType(Arrays.asList(null, new MockObject(fTypes.get(0).getIdentifier()))).toArray(new IStateType<?>[2]);
    	assertEquals(2, result.length);
    	assertNull(result[0]);
    	assertTrue(fTypes.get(0) == result[1]);
    }

    @Test
    public void test_State_getEffectType_All() {
    	int i = 0;
    	assertEquals(fTypes.size(), fState.getEffectType().size());
    	for (IStateType<?> type : fState.getEffectType()) {
    		assertEquals(fTypes.get(i++), type);
    	}
    }
    
    @Test
    public void test_State_getEffectType_All_AttemptToModifiy() {
    	int i = 0;
    	
    	try {
    		fState.getEffectType().add(createStateType(Integer.class, true, true, true));
    		fail();
    	} catch (UnsupportedOperationException e) {
    		// Success
    	}
    	
    	assertEquals(fTypes.size(), fState.getEffectType().size());
    	for (IStateType<?> type : fState.getEffectType()) {
    		assertEquals(fTypes.get(i++), type);
    	}
    }
    
    private <V> V getValidType(IStateType<V> type) {
    	if (Integer.class.equals(type.getType())) {
    		return type.getType().cast(dataIndex++);
    	} else if (String.class.equals(type.getType())) {
    		return type.getType().cast(Integer.toString(dataIndex++));
    	}
    	throw new UnsupportedOperationException();
    }
    
    private <V> Object getInvalidType(IStateType<V> type) {
    	if (Integer.class.equals(type.getType())) {
    		return Integer.toString(dataIndex++);
    	} else if (String.class.equals(type.getType())) {
    		return dataIndex++;
    	}
    	throw new UnsupportedOperationException();
    }
    
    private static class MockMappable extends AbstractIdentifier {
		public MockMappable(String identifier) {
			super(identifier);
		}
    }
    
    private static class MockObject {
    	private String fIdentifier;
		public MockObject(String identifier) {
			fIdentifier = identifier;
		}
		
		@Override
		public String toString() {
			return fIdentifier;
		}
    }
    
    private static class MockState extends State {
		public MockState(String identifier, int previousStateCount, List<IStateType<?>> types) {
			super(identifier, previousStateCount, types);
		}
		
		public void nextState() {
			fStates.add(new StateInternal(this, fStates.get(0)));
		}
    }
}
