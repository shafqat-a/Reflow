[{
            name: "File Header",
            fields:[
                {name:"record_type", start: "0", length: "2"},
                {name:"date_created", start:"2", length: "6"},
                {name:"time_created", start:"8", length: "8"}

            ], isValid : function (content) {
            if (content!=null & content.substr(0,2)=="00"){
                return true;
                }
            return false;
            }
        },
        {
            name: "Request Header",
            fields: [
                {name:"record_type", start: "0", length: "2"},
                {name:"request_number", start:"2",length: "6"},
                {name:"request_type", start: "17", length: "1"}
                //,
                //{name:"request_desc", start:"35", end: "75"}

            ], isValid : function (content) {
            if (content!=null && content.substr(0,2)=="01"){
                return true;
            }
            return false;
        }
        }

]