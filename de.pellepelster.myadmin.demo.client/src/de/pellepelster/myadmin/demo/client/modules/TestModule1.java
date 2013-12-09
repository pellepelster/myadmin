package de.pellepelster.myadmin.demo.client.modules;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.modules.BaseTestModule11Module;

public class TestModule1<VOType extends IBaseVO> extends BaseTestModule11Module
{
	private VOType vo;

	public TestModule1(ModuleVO moduleVO, final AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		MyAdmin.getInstance().getRemoteServiceLocator().getDictionaryService()
				.getDictionary(getDictionaryName(), new BaseErrorAsyncCallback<IDictionaryModel>()
				{

					@Override
					public void onSuccess(final IDictionaryModel dictionaryModel)
					{
						GenericFilterVO<VOType> filter = (GenericFilterVO<VOType>) ClientGenericFilterBuilder.createGenericFilter(dictionaryModel.getVOName())
								.addCriteria(IBaseVO.FIELD_ID, getVOId()).getGenericFilter();

						MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(filter, new BaseErrorAsyncCallback<List<VOType>>()
						{
							@Override
							public void onSuccess(List<VOType> result)
							{
								if (result.size() != 1)
								{
									throw new RuntimeException("error loading '" + dictionaryModel.getVOName() + "' with id " + getVOId());
								}

								TestModule1.this.vo = result.get(0);

								moduleCallback.onSuccess(TestModule1.this);
							}
						});

					}
				});

	}

	@Override
	public String getModuleId()
	{
		return super.getModuleName();
	}

	public VOType getVo()
	{
		return this.vo;
	}

}
