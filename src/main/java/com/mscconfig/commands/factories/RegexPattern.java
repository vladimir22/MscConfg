package com.mscconfig.commands.factories;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vladimir
 * Date: 27.08.13
 * Time: 15:54
 * Pattern of standard regex list
 */
public enum RegexPattern {

	SINGLEROW() {
		@Override
		public List<String> getRegexpList(String title) {
			List<String> list = new ArrayList();
			list.add(0,title+"(.+?\n){1}");   	 // get 1 row
			list.add(1,"(\\s*)"+title+"(\\W+)"); // delete  "spaces + title + NonWords(Digits)" before value
			list.add(2,"(\\s*?)\n"); 			 // delete " spaces +\n " after value
			return list;
		}
	},
	THREE_ROWS(){
		@Override
		public List<String> getRegexpList(String title) {
			List<String> list = new ArrayList();
			list.add(0,title+"(.+?\n){3}");   	// get 3 row
			list.add(1,title+"(.+?\n){2}");  	// delete first 2 unnecessary rows
			list.add(2,"(\\s*)( .*)\n"); 		// delete all spaces, other chars and \n after value
			return list;
		}
	},
	THREE_ROWS_WITH_DIGITS(){
		@Override
		public List<String> getRegexpList(String title) {
			List<String> list = new ArrayList();
			list.add(0,title+"(.+?\n){3}");   	// get 3 row
			list.add(1,title+"(.+?\n){2}");  	// delete first 2 unnecessary rows
			list.add(2,"^(\\d+)(\\s*)"); 		// delete "digits&spaces"
			list.add(3,"(\\s*):(\\s*)(.*)\n"); 	// delete "spaces:spaces/n"
			return list;
		}
	};

	/**
	 * Returns regexpList for find value by kind
	 * @param title
	 * @return
	 */
	public abstract List<String> getRegexpList(String title);



}
