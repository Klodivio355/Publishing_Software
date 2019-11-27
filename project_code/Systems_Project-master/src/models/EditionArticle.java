package models;

public class EditionArticle extends Article {

	private int editionArticleID;
	private int startingPage;
	private int endingPage;

	public EditionArticle()
	{
		super();
	}

	public EditionArticle(Article article)
	{
		super();
		super.setTitle(article.getTitle());
		super.setSummary(article.getSummary());
		super.setContent(article.getContent());
		super.setArticleID(article.getArticleID());
		super.setFinal(article.isFinal());
		super.setAccepted(article.isAccepted());
		super.setIssn(article.getIssn());
	}

	public int getEditionArticleID()
	{
		return editionArticleID;
	}

	public void setEditionArticleID(int editionArticleID)
	{
		this.editionArticleID = editionArticleID;
	}

	public int getStartingPage()
	{
		return startingPage;
	}

	public void setStartingPage(int startingPage)
	{
		this.startingPage = startingPage;
	}

	public int getEndingPage()
	{
		return endingPage;
	}

	public void setEndingPage(int endingPage)
	{
		this.endingPage = endingPage;
	}

	public String toString()
	{
		return "Article starting at page " + startingPage + " and ending at page " + endingPage
				+ " with following information: " + super.toString();
	}

}