package de.pellepelster.myadmin.demo.server.test.search;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.SearchResultItemVO;
import de.pellepelster.myadmin.client.web.services.search.IDictionarySearchService;
import de.pellepelster.myadmin.client.web.services.vo.IBaseEntityService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;

@ContextConfiguration(locations = { "classpath:/DemoServerSearchApplicationContext.xml" })
public class ElasticSearchTest extends BaseMyAdminJndiContextTest
{
	@Autowired
	private IDictionarySearchService searchService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testSimpleSearch()
	{
		for (String name : new String[] { "Philip", "Burton", "Deborah", "Caleb", "Chantale", "Jordan", "Sophia", "Emily", "Rana", "Carissa", "Uma", "Nadine",
				"Abra", "Melinda", "Sloane", "Allistair", "Scott", "Iola", "Zeph", "Jaime", "Ulla", "Blossom", "Sopoline", "Astra", "Remedios", "Maggy",
				"Regan", "Jessamine", "Steven", "Rhonda", "Chastity", "Desiree", "Ryan", "Julian", "Frances", "Rachel", "Gwendolyn", "Hiroko", "Quinlan",
				"Sierra", "Kyla", "Cody", "Melodie", "Tad", "Doris", "Benedict", "Fay", "Jack", "Desiree", "Galvin", "Zenaida", "Wilma", "Callie", "Aristotle",
				"Martina", "Holmes", "Mason", "Marvin", "Ronan", "Sylvester", "Jasmine", "Ferdinand", "Nelle", "Edward", "Tallulah", "Rogan", "Lani",
				"Savannah", "Quail", "Kieran", "Lillian", "Martena", "Julian", "Mariko", "Plato", "Cadman", "Alvin", "Basia", "Yvette", "Wynter", "Martina",
				"Plato", "Debra", "Ronan", "Burke", "Tad", "Jenna", "Kiayada", "Dominic", "Kelsie", "Ivana", "Lucy", "Savannah", "Dawn", "Raphael", "Sawyer",
				"Madison", "Ayanna", "Talon", "Eric" })
		{
			MyAdminUserVO user = new MyAdminUserVO();
			user.setUserName(name);
			this.baseEntityService.create(user);
		}

		List<SearchResultItemVO> searchResult = this.searchService.search("E");

		Assert.assertFalse(searchResult.isEmpty());
	}

	public void setSearchService(IDictionarySearchService searchService)
	{
		this.searchService = searchService;
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}
}
