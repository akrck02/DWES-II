/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package styles;

/**
 *
 * @author aketz
 */
public class Style {

    public final static String DEFAULT;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("<style>");

        builder.append(" * { ");
        builder.append(" font-family: 'Roboto', sans-serif; ");
        builder.append(" } ");

        builder.append(" body { ");
        builder.append(" display: flex; ");
        builder.append(" flex-direction: column; ");
        builder.append(" flex-direction: column; ");
        builder.append(" justify-content: center; ");
        builder.append(" align-items: center; ");
        builder.append(" padding: 2rem; ");
        builder.append(" } ");

        builder.append(" .text-error { ");
        builder.append(" background: rgb(255,45,34); ");
        builder.append(" color: #fff; ");
        builder.append(" padding: 1rem; ");
        builder.append(" border-radius:.35rem;");
        builder.append(" } ");

        builder.append(" .letter { ");
        builder.append("  text-align: center;");
        builder.append("  padding: .5rem;");
        builder.append("  min-width: 2rem;");
        builder.append("  background: #f1f1f1;");
        builder.append("  border-radius: .5rem;");
        builder.append(" } ");

        builder.append(" .letter > a { ");
        builder.append(" color: #404040;");
        builder.append(" text-decoration: none;");
        builder.append(" cursor : pointer ;");
        builder.append(" }");

        builder.append(" input[type=text]{");
        builder.append(" padding: .4rem;");
        builder.append(" margin: .4rem;");
        builder.append(" border: 1px solid #ccc;");
        builder.append(" background: #f5f5f5;");
        builder.append(" border-radius: .35rem;");
        builder.append(" }");

        builder.append(" input[type=submit]{");
        builder.append(" padding: .4rem;");
        builder.append(" margin: .4rem;");
        builder.append(" border: none;");
        builder.append(" box-shadow: 0px 2px 4px rgba(0,0,0,.15);");
        builder.append(" background: #f5f5f5;");
        builder.append(" border-radius: .45rem;");
        builder.append(" cursor: pointer;");
        builder.append(" }");

        builder.append("</style>");
        DEFAULT = builder.toString();
    }

}
