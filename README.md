<h1>PikaNetwork Api Wrapper</h1>
<p1>PikaNetwork api Wrapper Made in Java</p1>

<h1>How To Use</h1>
<p1>Just download the jar from the  <a href="https://github.com/AWTException/Pika-Api/releases/tag/main">release</a>  page and import it to your project</p1>

<h1>Usage Examples</h1>

<!-- <ul> -->
  
<li>Getting Leaderboard Stats:</li>

```java
public static void main(String[] args) {
    //request monthly leaderboard stats for all the modes(solo, doubles etc) of the gamemode bedwars starting from offset 0 with 100 as the limit
    JsonObject json = PikaAPI.getLeaderBoardStats(Gamemode.bedwars, Interval.monthly,
            Mode.ALL_MODES, Stat.kills, 0, 100);
    
    JsonArray players = json.getAsJsonArray("entries");//get the players

    JsonObject firstPlayer = players.get(0).getAsJsonObject();
    //passing the json from the beutifyJson function returns the readable json(adds \n, spaces and tabs)
    System.out.println(PikaAPI.beutifyJson(firstPlayer));
    //you should remove the quotes (") from the returned string before using it on the api
    String firstPlayerName = firstPlayer.get("id").toString();
    System.out.println("First Player Name: " + firstPlayerName);
}
```
<p1>Output:</p1>
```
{
  "place": 1,
  "value": "2291",
  "id": "Asmeet21",
  "clan": "BrokeN"
}
First Player Name: "Asmeet21"
```

<br> </br>

<li>Error Codes:</li>

<p1>If the request was unsuccessful, the function will return an error code as a json like on the following example</p1>
```java
//We do bad request that cant be completed
JsonObject statsJson = PikaAPI.getGamemodeStats(Gamemode.skywars, "INVALID_PLAYER_NAME", Interval.total, Mode.SOLO);
System.out.println(PikaAPI.beutifyJson(statsJson));
int errorCode = statsJson.get("code").getAsInt();
System.out.println("Error Code: " + errorCode);
```
<p1>Output:</p1>
```
{
  "code": 400
}
Error Code: 400
```

<!-- </ul> -->
