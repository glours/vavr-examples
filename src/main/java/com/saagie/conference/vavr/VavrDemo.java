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
                Case($(isIn("-v", "--version")), versionDocumentation.apply("") ),
                Case($(), invalidCommand.apply(helpDocumentation.apply(), arg.getOrElse("")))
        );
        System.out.println(commandDescription);
    }

    private static Function0<String> usageDocumentation =  () -> "usage: VavrDemo [options] \n-h, --help : Display command line help\n" ;
    private static Function1<String, String> versionDocumentation = previous -> previous.concat("-v, --version : Display version Information");
    private static Function0<String> helpDocumentation = usageDocumentation.andThen(versionDocumentation);
    private static Function2<String, String, String> invalidCommand = (previous, arg) -> "Unable to parse command line option : " + arg +"\n" + previous;

}
