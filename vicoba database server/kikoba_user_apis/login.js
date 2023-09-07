const { pool } = require("../database");
  
function login(req,res){
    var { email, pwd } = req.body;
    pool.query(
      'SELECT "userID", "firstName", "lastName", email, gender,to_char(dob, \'YYYY-MM-DD\') AS date, phone, "addressKey", region, district, ward, occupation_name FROM "address" JOIN "user" ON "addressID" = "addressKey"JOIN "occupation" ON "occupationKey" = "occupationID" WHERE email = \'' +
        email +
        "' AND pwd ='" +
        pwd +
        '\' ORDER BY "userID" DESC LIMIT 1;',
      (error, results) => {
        if (error) {
          throw error;
        }
        res.json(results.rows[0]);
      }
    );
}

module.exports = { login };