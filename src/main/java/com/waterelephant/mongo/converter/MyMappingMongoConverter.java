package com.waterelephant.mongo.converter;

import java.util.Date;
import java.util.List;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.AssociationHandler;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.util.StringUtils;

import com.google.common.base.CaseFormat;
import com.mongodb.DBObject;
import com.waterelephant.utils.DateUtil;

/**
 * <p>javaBean转mongo DbObject  
 * <p>1、实体bean中Date类型转成字符串类型（格式化Date yyyy-MM-dd HH:mm:ss）  
 * <p>2、实体bean中@Fiel注解（mongo）value="mg_time"转成时间戳添加到dbObject中
 * <p>3、在spring-mongodb.xml文件中bean id="mappingMongoConverter"下添加converterList属性节点
 * <p>4、将要做1、2处理的实体bean的类名全路径添加至converterList下
 * @author dinglinhao
 * @date 2018年11月9日17:34:36
 * @version 1.0
 * @since 1.8
 */
public class MyMappingMongoConverter extends MappingMongoConverter {
	
	private static final String FIELD_NAME = "mg_time";
	
	public MyMappingMongoConverter(DbRefResolver dbRefResolver,
			MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
		super(dbRefResolver, mappingContext);
	}

	private List<Object> listConverter;

	@Override
	protected void writeInternal(Object obj, DBObject dbo, MongoPersistentEntity<?> entity) {

		Document document = obj.getClass().getAnnotation(Document.class);
		
		if(document == null) {
			super.writeInternal(obj, dbo, entity);
		} else {
			
			String collection = document.collection();
			//collection 為空或不是以“mg_”開頭
			if(StringUtils.isEmpty(collection) || !collection.startsWith("mg_")) {
				super.writeInternal(obj, dbo, entity);
			} else {
				//自定义处理
				final PersistentPropertyAccessor accessor = entity.getPropertyAccessor(obj);
				// Write the properties
				entity.doWithProperties(new PropertyHandler<MongoPersistentProperty>() {
					public void doWithPersistentProperty(MongoPersistentProperty prop) {
						
						Object value = accessor.getProperty(prop);
						//将字段名转化成驼峰命名
						String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, prop.getName()).toLowerCase();
						
						if (null != value) {
							if(value instanceof Date) {
								Date time = (Date)value;
								//当@Field的value=mg_time时，
								if(FIELD_NAME.equals(prop.getFieldName())) {
									//1、添加gm_time字段，且值为时间戳类型
									dbo.put(FIELD_NAME, time.getTime());
									//2、将时间转字符串（yyyy-MM-dd HH:mm:ss）
									dbo.put(key, DateUtil.getDateString(time, DateUtil.yyyy_MM_dd_HHmmss));
								}
							}else if (!conversions.isSimpleType(value.getClass())) {
								writePropertyInternal(value, dbo, prop);
							} else {
								dbo.put(key, getPotentiallyConvertedSimpleWrite(value));
							}
						}
					}
				});
				
				entity.doWithAssociations(new AssociationHandler<MongoPersistentProperty>() {
					
					public void doWithAssociation(Association<MongoPersistentProperty> association) {
						
						MongoPersistentProperty inverseProp = association.getInverse();
						Object propertyObj = accessor.getProperty(inverseProp);
						
						if (null != propertyObj) {
							writePropertyInternal(propertyObj, dbo, inverseProp);
						}
					}
				});
			}
		}
	}

	/**
	 * Checks whether we have a custom conversion registered for the given value into an arbitrary simple Mongo type.
	 * Returns the converted value if so. If not, we perform special enum handling or simply return the value as is.
	 * 
	 * @param value
	 * @return
	 */
	private Object getPotentiallyConvertedSimpleWrite(Object value) {

		if (value == null) {
			return null;
		}

		Class<?> customTarget = conversions.getCustomWriteTarget(value.getClass(), null);

		if (customTarget != null) {
			return conversionService.convert(value, customTarget);
		} else {
			return Enum.class.isAssignableFrom(value.getClass()) ? ((Enum<?>) value).name() : value;
		}
	}

	public List<Object> getListConverter() {
		return listConverter;
	}

	public void setListConverter(List<Object> listConverter) {
		this.listConverter = listConverter;
	}

}
