using Microsoft.ClearScript;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Transformation
{
    public class ScriptFactory
    {
        public static ScriptEngine GetScriptEngine(string language)
        {
            if (language.ToLower().Equals("vbscript"))
            {
                return new Microsoft.ClearScript.Windows.VBScriptEngine();
            }
            throw new InvalidOperationException("Language not supported");
        }
    }
}
