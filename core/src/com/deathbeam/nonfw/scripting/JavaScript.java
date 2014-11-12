/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.scripting;

import com.badlogic.gdx.files.FileHandle;
import com.deathbeam.nonfw.Utils;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Thomas Slusny
 */
public class JavaScript extends ScriptRuntime {
    public static String getName() {
        return "JavaScript";
    }

    public static String getExtension() {
        return "js";
    }
    
    public JavaScript() {
        e = new ScriptEngineManager().getEngineByName("JavaScript");
        ScriptEngineFactory f = e.getFactory();
        
        System.out.println( "Engine name: " +f.getEngineName() );
        System.out.println( "Engine Version: " +f.getEngineVersion() );
        System.out.println( "LanguageName: " +f.getLanguageName() );
        System.out.println( "Language Version: " +f.getLanguageVersion() );
    }
    
    @Override
    public void invoke(String funct) {
        try {
            e.eval(funct + "();");
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }
    
    @Override
    public void invoke(String funct, String args) {
        try {
            e.eval(funct + "(" + args + ");");
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }
    
    @Override
    public void invoke(String funct, String arg1, String arg2) {
        try {
            e.eval(funct + "(" + arg1 + "," + arg2 + ");");
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }

    @Override
    public Object eval(FileHandle file) {
        try {
            return e.eval(file.reader());
        } catch (ScriptException ex) {
            Utils.warning("Scripting", ex.getMessage());
        }
        return null;
    }
}