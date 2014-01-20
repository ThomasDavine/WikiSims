package tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static lang.eng.Stopwords.DEFAULT_STOPWORDS;
import static lang.eng.Abbreviations.ABBREVIATION;

public class Eng_StopwrdTokenizer extends Tokenizer {

	/** The string to be tokenized. */
	private String str;

	/** Tokens. */
	private ArrayList<String> tokenList;

	/** Normalized tokens. */
	private ArrayList<String> normTokens;

	/** A regular expression for letters and numbers. */
	private static final String regexLetterNumber = "[a-zA-Z0-9]";

	/** A regular expression for non-letters and non-numbers. */
	private static final String regexNotLetterNumber = "[^a-zA-Z0-9]";

	/** A regular expression for separators. */
	private static final String regexSeparator = "[\\?!()\";/\\|`]";

	/** A regular expression for separators. */
	private static final String regexClitics = "'|:|-|'S|'D|'M|'LL|'RE|'VE|N'T|'s|'d|'m|'ll|'re|'ve|n't";

	/** A regular expression for note. */
	private static final String regexWikiNote = "\\[\\d+\\]";

	/** A regular expression for punctuation */
	private static final String punctuation = "(`|``|'|''|\"|\\[|\\]|\\(|\\)|\\.|,|\\/|:|;|!|\\?)";

	/** A regular expression for whitespace omissis */
	private static final String punctuationWordRegex = "[^\\'][\\w]*[(`|``|''|\"|\\[|\\]|\\(|\\)|\\.|,|:|;|!|\\?)][\\D][\\w]*";

	/**
	 * Constructs a string to be tokenized and an empty list for tokens.
	 * 
	 * @param str
	 *            a string to be tokenized
	 */
	public Eng_StopwrdTokenizer(String str) {
		this.str = str;
		tokenList = new ArrayList<String>();
		normTokens = new ArrayList<String>();

	}

	@Override
	protected void tokenize() {

		// Remove Wikipedia note
		str = str.replaceAll(regexWikiNote, "");

		// Changes tabs into spaces.
		str = str.replaceAll("\\t", " ");

		// Puts blanks around unambiguous separators.
		str = str.replaceAll("(" + regexSeparator + ")", " $1 ");

		// Puts blanks around commas that are not inside numbers.
		str = str.replaceAll("([^0-9]),", "$1 , ");
		str = str.replaceAll(",([^0-9])", " , $1");

		// Distinguishes single quotes from apostrophes by segmenting off
		// single quotes not preceded by letters.
		str = str.replaceAll("^(')", "$1 ");
		str = str.replaceAll("(" + regexNotLetterNumber + ")'", "$1 '");

		// Segments off unambiguous word-final clitics and punctuation.
		str = str.replaceAll("(" + regexClitics + ")$", " $1");
		str = str.replaceAll("(" + regexClitics + ")(" + regexNotLetterNumber
				+ ")", " $1 $2");

		// Deals with periods.
		String[] words = str.trim().split("\\s+");
		Pattern p1 = Pattern.compile(".*" + regexLetterNumber + "\\.");
		Pattern p2 = Pattern
				.compile("^([A-Za-z]\\.([A-Za-z]\\.)+|[A-Z][bcdfghj-nptvxz]+\\.)$");
		Pattern p3 = Pattern.compile(punctuationWordRegex);

		for (String word : words) {
			Matcher m1 = p1.matcher(word);
			Matcher m2 = p2.matcher(word);
			Matcher m3 = p3.matcher(word);

			if (m3.matches()) {

				// Split words with punctuation inside ("word.split" becomes
				// "word","split")
				// note that - is not considered punctuation
				String[] a = word.split(punctuation);
				for (String word2 : a) {
					tokenList.add(word2);
				}

			} else if (m1.matches() && !ABBREVIATION.contains(word)
					&& !m2.matches()) {

				// Segments off the period.
				tokenList.add(word.substring(0, word.length() - 1));
				tokenList.add(word.substring(word.length() - 1));

			} else {
				tokenList.add(word);
			}
		}
	}

	/**
	 * A bland normalization. Stop-words, punctuation, and clitics are removed,
	 * tokens are normalized to lower case.
	 */
	private void normalize() {

		for (int i = 0; i < this.tokenList.size(); i++) {
			// Single characters are not considered
			if ((tokenList.get(i).length() > 1)&&(!tokenList.get(i).matches(regexClitics))) {
				if (!tokenList.get(i).matches(punctuation)
						&& !tokenList.get(i).toLowerCase().matches(
								DEFAULT_STOPWORDS)
						&& !ABBREVIATION.contains(tokenList.get(i))) {
							// Delete the last character if this is a punctuation
							if (tokenList.get(i).endsWith(punctuation)
									&& (tokenList.get(i).length() > 2)) {
									normTokens.add(tokenList.get(i).toLowerCase()
											.substring(0, tokenList.get(i).length() - 1));
					} else {
						normTokens.add(tokenList.get(i).toLowerCase());
					}
				}
			}
		}
	}

	@Override
	public ArrayList<String> getTokens() {
		tokenize();
		normalize();
		return this.normTokens;
	}
}
