package com.wys.ssm.base.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class AlikAssert {

    /**
     * Assert a boolean expression, throwing <code>BusinessException</code> if
     * the test result is <code>false</code>.
     * 
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
     * </pre>
     * 
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            BusinessException.throwMessage(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     * 
     * <pre class="code">
     * Assert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     * 
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * 断言对象不为null，为null则抛出异常
     * 
     * @param object
     * @param message
     */
    public static void isNotNull(Object object, String message) {
        if (object == null) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * 断言字符串不为空，为空则抛出异常
     * 
     * @param str
     * @param message
     */
    public static void isNotEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * 断言字符串不为空，为空则抛出异常
     * 
     * @param str
     * @param message
     */
    public static void isNotBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * 断言集合不为空，为空则抛出异常
     * 
     * @param message
     */
    public static void isNotEmpty(Collection<?> coll, String message) {
        if (CollectionUtils.isEmpty(coll)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * 
     * <pre class="code">
     * Assert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     * 
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty(array, &quot;The array must have elements&quot;);
     * </pre>
     * 
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the object array is <code>null</code> or has
     *             no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            BusinessException.throwMessage(message);
        }
    }

    public static void empty(Object[] array, String message) {
        if (ArrayUtils.isNotEmpty(array)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the
     * array is empty!
     * 
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     * 
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the object array contains a
     *             <code>null</code> element
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    BusinessException.throwMessage(message);
                }
            }
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * 
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     * 
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the collection is <code>null</code> or has
     *             no elements
     */
    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            BusinessException.throwMessage(message);
        }
    }

    public static void empty(Collection collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code>
     * and must have at least one entry.
     * 
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     * 
     * @param map the map to check
     * @param message the exception message to use if the assertion fails
     * @throws BusinessException if the map is <code>null</code> or has no
     *             entries
     */
    public static void notEmpty(Map map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * <p>
     * Checks if a String is empty ("") or null.
     * </p>
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * 
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the
     * String. That functionality is available in isBlank().
     * </p>
     * 
     * @param str the String to check, may be null
     * @throws BusinessException if the str is empty
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * 
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str the String to check, may be null
     * @throws BusinessException if the str is blank
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            BusinessException.throwMessage(message);
        }
    }

    public static void notEquals(String str1, String str2, String message) {
        if (StringUtils.equals(str1, str2)) {
            BusinessException.throwMessage(message);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * 
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param type the type to check against
     * @param obj the object to check
     * @param message a message which will be prepended to the message produced
     *            by the function itself, and which may be used to provide
     *            context. It should normally end in a ": " or ". " so that the
     *            function generate message looks ok when prepended to it.
     * @throws BusinessException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            BusinessException.throwMessage(message + "Object of class [" + (obj != null ? obj.getClass().getName() : "null")
                    + "] must be an instance of " + type);
        }
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is
     * <code>true</code>.
     * 
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     * 
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param message a message which will be prepended to the message produced
     *            by the function itself, and which may be used to provide
     *            context. It should normally end in a ": " or ". " so that the
     *            function generate message looks ok when prepended to it.
     * @throws BusinessException if the classes are not assignable
     */
    @SuppressWarnings("unchecked")
    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            BusinessException.throwMessage(message + subType + " is not assignable to " + superType);
        }
    }

}
