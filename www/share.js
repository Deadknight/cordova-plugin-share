module.exports = function(text,title,mimetype,action,success,error){
  if(typeof text !== "string") {
    text = "";
  }
  if(typeof title !== "string") {
    title = "Share";
  }
  if(typeof mimetype !== "string") {
    mimetype = "text/plain";
  }
  if(typeof action !== "number") {
    action = 0;
  }
  cordova.exec(success,error,"Share","share",[text,title,mimetype,action]);
  return true;
};
