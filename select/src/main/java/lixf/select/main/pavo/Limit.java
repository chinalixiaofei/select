package lixf.select.main.pavo;

/**
 * @author lixf
 */
public class Limit {

	public final int skip;

	public final int limit;

	public Limit(Integer limit) {

		this.skip = 0;
		this.limit = limit;
	}
	public Limit(Integer skip, Integer limit) {

		this.skip = skip;
		this.limit = limit;
	}

}
