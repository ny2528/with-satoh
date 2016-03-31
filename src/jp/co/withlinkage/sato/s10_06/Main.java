//	このソースコードがコンパイルおよび実行できないみたいで原因調査中です。
package jp.co.withlinkage.sato.s10_06;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.xml");
		Document doc = DocumentBuilderFactory.newInstance().
				newDocumentBuilder().parse(is);
		Element hero = doc.getDocumentElement();
		Element weapon = findChildByTag(hero, "weapon");
		Element power = findChildByTag(weapon, "power");
		String value = power.getTextContent();
		System.out.println(value);
	}
//	指定された名前を持つタグの最初の子タグを返す
	static Element findChildByTag(Element self, String name)
		throws Exception{
		NodeList children = self.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			if(children.item(i) instanceof Element){
			Element e = (Element) children.item(i);
				if(e.getTagName().equals(name)){
				return e;
				}
			}
		}
		return null;
	}
}
