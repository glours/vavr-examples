package com.saagie.conference.vavr;


import io.vavr.API;
import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.Array;
import io.vavr.control.Option;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.isIn;


public class VavrDemo
{
    public static void main( String[] args )
    {
        Option<String> arg = Array.of(args).headOption();
        String commandDescription = API.Match(arg.getOrElse("")).of(
                Case($(isIn("-h", "--help")), helpDocumentation.apply()),
                Case($(isIn("-v", "--version")), versionDocumentation.apply(Option.none()) ),
                Case($(), invalidCommand.apply(helpDocumentation.apply(), arg))
        ).getOrElse("Error when parsing argument");
        System.out.println(commandDescription);
    }

    private static Function0<Option<String>> usageDocumentation =  () -> Option.of("usage: VavrDemo [options] \n-h, --help : Display command line help\n") ;
    private static Function1<Option<String>, Option<String>> versionDocumentation = previous -> previous.map(prev -> prev + "-v, --version : Display version Information");
    private static Function0<Option<String>> helpDocumentation = usageDocumentation.andThen(versionDocumentation);
    private static Function2<Option<String>, Option<String>, Option<String>> invalidCommand = (previous, arg) -> previous.map(prev -> "Unable to parse command line option : " + arg.getOrElse("") +"\n" + prev) ;
}
