<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session = request.getSession(true);
    session.invalidate();
    
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Test.com - home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/main.css"/>
        <style>
            td {
                padding: .35rem;
            }
            
            h1 {
                padding: .35rem;
            }

            input[type=text] {
                margin: .5rem 0;
                width: 10rem;
            }
            
            label:hover, label:focus,label + input[type=checkbox]{
                color: var(--accent-color);
            }
        </style>
    </head>
    <body class="box-center box-column">
        <h1>Preguntas en test</h1>

        <form action="http://localhost:8080/Ejercicio3/ProcesoPregunta" method="post">
            <table>
                <tr>
                    <td><label class="column" for="name">Nombre: </label></td>
                    <td><input name="name" type="text" id="name"></td>
                </tr>
                <tr>
                    <td><label class="column" for="question-number">Numero de preguntas: </label></td>
                    <td><input name="question-number" type="text" id="question-number"></td>
                </tr>
                <tr>
                    <td><label class="column"><input name="hints" type="checkbox"> Mostrar pistas</label></td>
                    <td><input name="start" type="submit" value="Comenzar"></td>
                </tr>
            </table>
            <p class="text-error text-center">
                <% 
                    if(request.getParameter("InvalidNumber") != null)
                        out.print("Introdúzca un número válido");
                    
                    if(request.getParameter("InvalidName") != null)
                        out.print("Introduzca nombre");
                %>
            </p>
        </form>
    </body>
</html>
