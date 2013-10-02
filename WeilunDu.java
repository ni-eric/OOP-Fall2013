import java.io.File;
import java.io.IOException;
import java.io.Reader;

import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;


/**
 * 
 * 
 * @author Weilun Du
 *
 */
public class WeilunDu extends xtc.util.Tool {
	public WeilunDu(){
		//
	}
	
	public String getName() {
		String myName="Weilun Du";
		return myName;
	}
	
	public Node parse(Reader in, File file) throws IOException, ParseException {
		JavaFiveParser parser =
			      new JavaFiveParser(in, file.toString(), (int)file.length());
		Result result = parser.pCompilationUnit(0);
	    return (Node)parser.value(result);
	}
	
	public File locate(String name) throws IOException {
	    File file = super.locate(name);
	    if (Integer.MAX_VALUE < file.length()) {
	      throw new IllegalArgumentException(file + ": file too large");
	    }
	    return file;
	  }
	  public void process(Node node) {
		    new Visitor() {
		      public void visitCompilationUnit(GNode n) {
		        visit(n);
		        runtime.console().flush();
		      }

		      public void visitClassDeclaration(GNode n) {
		        runtime.console().p("Enter Scope at ").loc(n).pln();
		        visit(n.getNode(5));
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }

		      public void visitInterfaceDeclaration(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        visit(n.getNode(4));
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }

		      public void visitConstructorDeclaration(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        visit(n.getNode(5));
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }

		      public void visitMethodDeclaration(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        Node body = n.getNode(7);
		        if (null != body) visit(body);
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }

		      public void visitBlock(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        visit(n);
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }

		      public void visitForStatement(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        visit(n.getNode(1));
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }
		      
		      public void visitWhileStatement(GNode n){
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		    	visit(n.getNode(1));
		    	runtime.console().p("Exit Scope at ").loc(n).pln();
		      }
		      public void visitSwitchStatement(GNode n){
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
			    visit(n.getNode(1));
			    runtime.console().p("Exit Scope at ").loc(n).pln();
		      }
		      
		      void	visitConditionalStatement(GNode n){
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
				Node body=n.getNode(2);
				if(null!=body) visit(body);
				runtime.console().p("Exit Scope at ").loc(n).pln();
		      }
		      
		      public void visitNewClassExpression(GNode n) {
		    	runtime.console().p("Enter Scope at ").loc(n).pln();
		        Node body = n.getNode(4);
		        if (null != body)	visit(body);
		        runtime.console().p("Exit Scope at ").loc(n).pln();
		      }
		      
		      public void visit(Node n) {
		        for (Object o : n) {
		          // The scope belongs to the for loop!
		          if (o instanceof Node) dispatch((Node)o);
		        }
		      }
		      
		    }.dispatch(node);
		  }
	  public static void main(String args[]){
			new WeilunDu().run(args);
		}
		
}
