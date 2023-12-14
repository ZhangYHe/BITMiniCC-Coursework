package bit.minisys.minicc.icgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import bit.minisys.minicc.parser.ast.ASTIdentifier;
import bit.minisys.minicc.parser.ast.ASTIntegerConstant;
import bit.minisys.minicc.parser.ast.ASTNode;

public class MyICPrinter {
    private  List<Quat> quats;
    public MyICPrinter(List<Quat> quats) {
        this.quats = quats;
    }
    public void print(String filename) {
        StringBuilder sb = new StringBuilder();
        for (Quat quat : quats) {
            if(quat.getScope()!=null){
                Integer cursor = quat.getCursor();
                String scope = quat.getScope();
                sb.append(cursor.toString()+":"+scope+":\n");
            }else{
                Integer cursor = quat.getCursor();
                String op = quat.getOp();
                String res = astStr(quat.getRes());
                String opnd1 = astStr(quat.getOpnd1());
                String opnd2 = astStr(quat.getOpnd2());

                sb.append(cursor.toString()+":    ("+op+","+ opnd1+","+opnd2 +"," + res+")\n");
            }

        }
        // write
        try {
            FileWriter fileWriter = new FileWriter(new File(filename));
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String astStr(ASTNode node) {
        if (node == null) {
            return "";
        }else if (node instanceof ASTIdentifier) {
            return ((ASTIdentifier)node).value;
        }else if (node instanceof ASTIntegerConstant) {
            return ((ASTIntegerConstant)node).value+"";
        }else if (node instanceof TemporaryValue) {
            return ((TemporaryValue)node).name();
        }else if (node instanceof CursorValue){
            if (((CursorValue) node).name()!=null)
                return ((CursorValue)node).name();
            else
                return "";
        }else{
            return "";
        }
    }
}
