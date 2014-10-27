package com.dhernandez.calculator.utils;

 public class Test
 {

   public static void main(String[] args)
   {

     Parser parser = new Parser();
     try
     {
       ExpressionNode expr = parser.parse("pi");
       expr.accept(new SetVariable("pi", Math.PI));
       System.out.println("The value of the expression is "+expr.getValue());

     }
     catch (ParserException e)
     {
       System.out.println(e.getMessage());
     }
     catch (EvaluationException e)
     {
       System.out.println(e.getMessage());
     }
   }
 }
