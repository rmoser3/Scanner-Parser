package main;

public class keywordTable 
{
	keyword[] keyTable = new keyword[100];

    public keywordTable()
    {
        keyTable[0] = new keyword("import", 1);
        keyTable[1] = new keyword("use", 2);
        keyTable[2] = new keyword("function", 3);
        keyTable[3] = new keyword("main", 4);
        keyTable[4] = new keyword("is", 5);
        keyTable[5] = new keyword("variables", 6);
        keyTable[6] = new keyword("define", 7);
        keyTable[7] = new keyword("of", 8);
        keyTable[8] = new keyword("type", 9);
        keyTable[9] = new keyword("pointer", 10);
        keyTable[10] = new keyword("double", 11);
        keyTable[11] = new keyword("begin", 12);
        keyTable[12] = new keyword("display", 13);
        keyTable[13] = new keyword("input", 14);
        keyTable[14] = new keyword("if", 15);
        keyTable[15] = new keyword("then", 16);
        keyTable[16] = new keyword("else", 17);
        keyTable[17] = new keyword("endif", 18);
        keyTable[18] = new keyword("not", 19);
        keyTable[19] = new keyword("greater", 20);
        keyTable[20] = new keyword("or", 21);
        keyTable[21] = new keyword("equal", 22);
        keyTable[22] = new keyword("return", 23);
        keyTable[23] = new keyword("forward", 24);
        keyTable[24] = new keyword("description", 25);
        keyTable[25] = new keyword("global", 26);
        keyTable[26] = new keyword("declarations", 27);
        keyTable[27] = new keyword("symbol", 28);
        keyTable[28] = new keyword("string", 29);
        keyTable[29] = new keyword("struct", 30);
        keyTable[30] = new keyword("char", 31);
        keyTable[31] = new keyword("integer", 32);
        keyTable[32] = new keyword("short", 33);
        keyTable[33] = new keyword("real", 34);
        keyTable[34] = new keyword("float", 35);
        keyTable[35] = new keyword("long", 36);
        keyTable[36] = new keyword("implementations",37);
        keyTable[37] = new keyword("endfun", 38);
        keyTable[38] = new keyword("and", 39);
        keyTable[39] = new keyword("to", 40);
        keyTable[40] = new keyword("from", 41);
        keyTable[41] = new keyword("for", 42);
        keyTable[42] = new keyword("do", 43);
        keyTable[43] = new keyword("endfor", 44);
        keyTable[44] = new keyword("while", 45);
        keyTable[45] = new keyword("endwhile", 46);
        keyTable[46] = new keyword("case", 47);
        keyTable[47] = new keyword("elseif", 48);
        keyTable[48] = new keyword("downto", 49);
        keyTable[49] = new keyword("array", 50);
        keyTable[50] = new keyword("specifications", 51);
        keyTable[51] = new keyword("enumerate", 52);
        keyTable[52] = new keyword("constants", 53);
        keyTable[53] = new keyword("structures", 54);
        keyTable[54] = new keyword("byte", 55);
        keyTable[55] = new keyword("exit", 56);
        keyTable[56] = new keyword("parameters", 57);
        keyTable[57] = new keyword("increment", 58);
        keyTable[58] = new keyword("call", 59);
        keyTable[59] = new keyword("using", 60);
        keyTable[60] = new keyword("unsigned", 61);
        keyTable[61] = new keyword("bool", 62);
        keyTable[62] = new keyword("set", 63);
        keyTable[63] = new keyword("band", 64);
        keyTable[64] = new keyword("bor", 65);
        keyTable[65] = new keyword("bxor", 66);
        keyTable[66] = new keyword("negate", 67);
        keyTable[67] = new keyword("endstruct", 68);
        keyTable[68] = new keyword("definetype", 69);
        keyTable[69] = new keyword("void", 70); 
        keyTable[70] = new keyword("address", 71);
        keyTable[71] = new keyword("structype", 72);
        keyTable[72] = new keyword("count", 73);
        keyTable[73] = new keyword("persistent", 74);
        keyTable[74] = new keyword("static", 75);
        keyTable[75] = new keyword("shared", 76);
        keyTable[76] = new keyword("true", 77);
        keyTable[77] = new keyword("false", 78);
        keyTable[78] = new keyword("alters", 79);
        keyTable[79] = new keyword("preserves", 80);
        keyTable[80] = new keyword("produces", 81);
        keyTable[81] = new keyword("consumes", 82);
        keyTable[82] = new keyword("repeat", 83);
        keyTable[83] = new keyword("until", 84);
        keyTable[84] = new keyword("endrepeat", 85);
        keyTable[85] = new keyword("endcase", 86);
        keyTable[86] = new keyword("break", 87);
        keyTable[87] = new keyword("DataT",89);
        keyTable[88] = new keyword("listT",90);
        keyTable[89] = new keyword("destroy",91);
        keyTable[90] = new keyword("create",92);
        keyTable[91] = new keyword("loop",93);
        keyTable[92] = new keyword("plist",94);
        keyTable[93] = new keyword("endenum",95);
        keyTable[94] = new keyword("enum",96);

    }

    public int isKeyword(String str)
    {
        //Remember to change termination condition as we move along 
        for(int i = 0; i < 95; i++)
        {
            if(str.equals(keyTable[i].name))
            {
                return i;
            }
        }
        return -1;
    }
}
