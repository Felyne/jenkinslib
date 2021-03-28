
/**
 * A Class description
 */
class Person {
    /** the name of the person */
    String name

    /**
     * Creates a greeting method for a certain person.
     *
     * @param otherPerson the person to greet
     * @return a greeting message
     */
    String greet(String otherPerson) {
       "Hello ${otherPerson}"
    }
}

def person = new Person(name: "chen")
println(person.greet("liming"))

def a = 'ahah'

assert a.getClass()==String

if (a.contains('ah')) {
    println("ok")
} else {
    println("not ok")
}

def judge(buildType) {
    switch(buildType) {
        case "maven":
            println("maven")
            break;
        case "ant":
            break;
        default:
            return
    }
}

judge("maven")

def response = readJSON text: "${scanResult}"
println(scanResult)

//原生方法
import groovy.json.*

def GetJson(text){
    def prettyJson = JsonOutput.prettyPrint(text) 
    new JsonSlurperClassic().parseText(prettyJson)
}