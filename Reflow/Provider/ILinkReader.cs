using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Reflow.Provider
{
    public interface ILinkReader
    {
        void Initialize(IDataLink link);
        string Command
        {
            get;
            set;
        }

        bool Open();
        bool Open(string command);

        IDataReader Reader
        {
            get;
        }

    }
}
