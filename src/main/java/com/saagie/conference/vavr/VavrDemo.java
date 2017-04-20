package com.saagie.conference.vavr;


import javaslang.API;
import javaslang.Function0;
import javaslang.Function1;
import javaslang.Function2;
import javaslang.collection.Array;
import javaslang.control.Option;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.Predicates.isIn;

public class VavrDemo
{
    public static void main( String[] args )
    {
        Option<String> arg = Array.of(args).headOption();
        String commandDescription = API.Match(arg.getOrElse("")).of(
                Case(isIn("-h", "--help"), helpDocumentation.apply()),
                Case(isIn("-v", "--version"), versionDocumentation.apply(Option.none()) ),
                Case($(), invalidCommand.apply(helpDocumentation.apply(), arg))
        ).getOrElse("Error when parsing argument");
        System.out.println(commandDescription);
    }

    private static Function0<Option<String>> usageDocumentation =  () -> Option.of("usage: VavrDemo [options] \n") ;
    private static Function1<Option<String>, Option<String>> versionDocumentation = previous -> previous.map(prev -> prev + "-v, --version : Display version Information");
    private static Function0<Option<String>> helpDocumentation = usageDocumentation.andThen(versionDocumentation);
    private static Function2<Option<String>, Option<String>, Option<String>> invalidCommand = (previous, arg) -> previous.map(prev -> "Unable to parse command line option : " + arg.getOrElse("") +"\n" + prev) ;
}
