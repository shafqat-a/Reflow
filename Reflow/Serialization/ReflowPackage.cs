using Reflow.Tasks;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Reflow.Serialization
{
    public class ReflowPackage
    {
        public ReflowJob Job { get; set; }

        public static bool SavePackage(Stream stream, ReflowJob job)
        {
            List<Type> types = new List<Type>();
            types.Add(typeof(DataFlowTask));
            XmlSerializer serilizer = new XmlSerializer (typeof( ReflowJob), types.ToArray());
            return false;
        }

        public static ReflowJob LoadPackage (Stream stream)
        {
            return null;
        }
    }
}
