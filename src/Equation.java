import java.util.ArrayList;

public class Equation {
    
    private ArrayList<String> posfisxEquation;
    private StackLinkedList<String> operatorStack;
    private StackLinkedList<Double> operandStack;
    
    private static final String operators = "^*/+-";
    
    public Equation(String infixEquation){
        this.operatorStack = new StackLinkedList<String>();
        this.posfisxEquation = this.infixToPosfix(infixEquation);   // Convertir a la forma posfix
    }
    
    public ArrayList<String> infixToPosfix(String infix){
        ArrayList<String> tmp = new ArrayList<String>();
        infix = infix.toLowerCase();
        infix = infix.replaceAll("\\s+","");    // Quitar todos los espacios en blanco
        
        String[] elements = infix.split("(?<=[-+/*x()^])|(?=[-+/*x()^])");  // Separar en operadores y operandos
        
        for(int i = 0; i < elements.length; i++){
            if(elements[i].matches("-?\\d+(\\.\\d+)?")){
                // Si el elemento es un numero
                // Atrás de un número no puede ir un parentesis
                if(i > 0 && elements[i-1].contains(")")){
                    throw new EquationNotWellFormatedException();
                }
                
                tmp.add(elements[i]);
            }else if (operators.contains(elements[i])){
                // Si el elemento es un operador
                // Manejar excepciones
                if(i == elements.length-1){ // Si hay un operador al final
                    throw new EquationNotWellFormatedException();
                }
                if(i == 0 && (elements[i].equals("*") || elements[i].equals("/") || elements[i].equals("^") || elements[i].equals("+"))){   // Si hay un operador al inicio que no sea -
                    throw new EquationNotWellFormatedException();
                }
                // Excepciones del signo -
                if(elements[i].equals("-")){
                    if(i == 0){     // Si es el primer elemento
                        if(elements[i+1].matches("-?\\d+(\\.\\d+)?")){
                            // Si el siguiente elemento es un numero
                            elements[i+1] = elements[i] + elements[i+1];
                        }else if(elements[i+1].equals("x") || elements[i+1].equals("(")){
                            // Si el siguiente elemento es un parentesis la variable
                            while(!this.operatorStack.isEmpty() && !this.operatorStack.peek().equals("(") && this.hasEqualOrMorePrecedence("*", this.operatorStack.peek())){
                                tmp.add(this.operatorStack.pop());
                            }
                            this.operatorStack.push("*");
                            tmp.add("-1");
                        }else{
                            throw new EquationNotWellFormatedException();
                        }
                        continue;
                    }else if(elements[i-1].equals("/") || elements[i-1].equals("^")){
                        // Poner excepcion
                        elements[i+1] = elements[i] + elements[i+1];
                        continue;
                    }else if(elements[i-1].equals("(") && !elements[i+1].equals("x")){
                        elements[i+1] = elements[i] + elements[i+1];
                        continue;
                    }else if(elements[i-1].equals("(") && elements[i+1].equals("x")){
                        this.vaciarStack(tmp, "*");
                        tmp.add("-1");
                        continue;
                    }else if(elements[i-1].equals("+") || elements[i-1].equals("-")){
                        throw new EquationNotWellFormatedException();
                    }
                }
                
                // Excepciones del signo +
                if(elements[i].equals("+")){
                    if(operators.contains(elements[i-1]) || elements[i-1].equals("(")){
                        throw new EquationNotWellFormatedException();
                    }
                }
                // Excepciones del signo *
                if(elements[i].equals("*")){
                    if(operators.contains(elements[i-1]) || elements[i-1].equals("(")){
                        throw new EquationNotWellFormatedException();
                    }
                }
                // Excepciones del signo /
                if(elements[i].equals("/")){
                    if(operators.contains(elements[i-1]) || elements[i-1].equals("(")){
                        throw new EquationNotWellFormatedException();
                    }
                }
                // Excepciones del signo ^
                if(elements[i].equals("^")){
                    if(operators.contains(elements[i-1]) || elements[i-1].equals("(")){
                        throw new EquationNotWellFormatedException();
                    }
                }
                
                this.vaciarStack(tmp, elements[i]);
                
            }else if(elements[i].equals("(")){
                // Si el elemento es un parentesis abriendose
                if(i >= 1 && (elements[i-1].matches("-?\\d+(\\.\\d+)?") || elements[i-1].equals(")") || elements[i-1].equals("x"))){
                    this.vaciarStack(tmp, "*");
                }
                this.operatorStack.push(elements[i]);
                
            }else if(elements[i].equals(")")){
                // Si el elemento es un parentesis cerrandose
                // No puede haber ningún operador detrás
                if(i > 0 && (operators.contains(elements[i-1]))){
                    throw new EquationNotWellFormatedException();
                }
                // Sacar todos los operadores del stack hasta encontrar el (, de caso contrario mandar error
                while(true){
                    try{
                        if(this.operatorStack.peek().equals("(")){
                            // Encontramos el final
                            this.operatorStack.pop();
                            break;
                        }else{
                            tmp.add(this.operatorStack.pop());
                        }
                    }catch(Exception e){
                        // Si llego hasta aqui, no habia ( en el stack
                        throw new EquationNotWellFormatedException();
                    }
                }
            }else if (elements[i].equals("x")){
                // Si el elemento es la variable
                // Si el elemento anterior es un numero meter un * al stack
                if(i >= 1 && (elements[i-1].matches("-?\\d+(\\.\\d+)?") || elements[i-1].equals(")") || elements[i-1].equals("x"))){
                    this.vaciarStack(tmp, "*");
                }
                // Luego meter la variable a tmp
                tmp.add(elements[i]);
            }else{
                // Hay un error en la ecuacion
                System.out.println(elements[i]);
                throw new EquationNotWellFormatedException();
            }
        }
        // Vaciar el stack en tmp
        while(!this.operatorStack.isEmpty()){
            if(!this.operatorStack.peek().equals("(")){
                tmp.add(this.operatorStack.pop());      // Aniadir operadores faltantes
            }else{
                this.operatorStack.pop();       // Quitar el parentesis inutil
            }
        }
        return tmp;
    }
    
    public double evalEquation(double val){
        // Recorrer toda la ecuacion posfix
        // Asume que el posfix es correcto
        // Cuando encuentre un operando lo agrego al stack
        // Si encuentro un operador, hago la operación con los últimos dos valores del stack
        this.operandStack = new StackLinkedList<Double>();
        for(int i = 0; i < this.posfisxEquation.size(); i++){
            if(this.posfisxEquation.get(i).matches("-?\\d+(\\.\\d+)?")){       // Si es un operando
                this.operandStack.push(Double.parseDouble(this.posfisxEquation.get(i)));
            }else if(this.operators.contains(this.posfisxEquation.get(i))){    // Si es un operador
                try{
                    double oper1 = this.operandStack.pop();
                    double oper2 = this.operandStack.pop();
                    
                    switch(this.posfisxEquation.get(i)){
                    case "*":
                        this.operandStack.push(oper2*oper1);
                        break;
                    case "/":
                        this.operandStack.push(oper2/oper1);
                        break;
                    case "^":
                        this.operandStack.push(Math.pow(oper2, oper1));
                        break;
                    case "+":
                        this.operandStack.push(oper2+oper1);
                        break;
                    case "-":
                        this.operandStack.push(oper2-oper1);
                        break;
                    }
                }catch(RuntimeException e){
                    throw new EquationNotWellFormatedException();
                }
            }else if(this.posfisxEquation.get(i).equals("x")){
                this.operandStack.push(val);
            }
        }
        return this.operandStack.pop();
    }
    
    public double evalLeftLimit(double val){
        return this.evalEquation(val-.1);
    }
    
    public double evalRightLimit(double val){
        return this.evalEquation(val+.1);
    }
    
    private void vaciarStack(ArrayList<String> tmp, String operacion){
        while(!this.operatorStack.isEmpty() && !this.operatorStack.peek().equals("(") && this.hasEqualOrMorePrecedence(operacion, this.operatorStack.peek())){
            tmp.add(this.operatorStack.pop());
        }
        this.operatorStack.push(operacion);
    }
    
    private boolean hasEqualOrMorePrecedence(String op1, String op2){
        // Checa si el operador op2 es tiene mayor o igual precedencia que el op1
        String[] precedencia = {"+-", "*/", "^"};
        int p1 = -1, p2 = -1;     // Valor de precedencia de ambos operandos
        for(int i = 0; i < precedencia.length; i++){
            if(precedencia[i].contains(op1)){
                p1 = i;
            }
            if(precedencia[i].contains(op2)){
                p2 = i;
            }
        }
        return p2 >= p1;
    }
    
    public void resetEquation(String infixEquation){
        this.operatorStack = new StackLinkedList<String>();
        this.posfisxEquation = this.infixToPosfix(infixEquation);
    }
}