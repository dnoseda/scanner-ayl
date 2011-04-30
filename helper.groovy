def allLetters = "abcdefghijklmnopqrstuvwxyz"
allLetters += allLetters.toUpperCase()
for(l in allLetters){
	println "    $l: ident"
}
