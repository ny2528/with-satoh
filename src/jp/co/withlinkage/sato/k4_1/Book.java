package jp.co.withlinkage.sato.k4_1;
import java.util.Date;

public class Book implements Comparable<Book>, Cloneable {
	private String title;
	private Date publishDate;
	private String comment;


	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Book)) return false;
		Book r = (Book) o;
		if(!(this.title.trim().equals(r.title.trim()))){
			return false;
		}
		if(!(this.publishDate.equals(r.publishDate))){
			return false;
		}
		return true;
	}

	public int hashCode(){
		int result = 37;
		result = result * 31 + title.hashCode();
		result = result * 31 + publishDate.hashCode();
		return result;
	}

	public int compareTo(Book o){
		return this.publishDate.compareTo(o.publishDate);
	}

	public Book clone(){
		Book result = new Book();
		result.title = this.title;
		// ↓ publishDateにcloneを付ける理由とは?
		//TODO: 同じ参照を指すから: this.publishDate#setXX()がcloneのそれにも反映されてしまう。（古いメソッドだが）
		// ↓ (Date)によるキャスト化の理由とは？
		//TODO: #clone() の戻り値がObjectだから
		result.publishDate = (Date) this.publishDate.clone();
		result.comment = this.comment;
		return result;
	}
}
