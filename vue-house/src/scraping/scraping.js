import puppeteer from "puppeteer";

async function scrapeNewsHeadlines(url) {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto(url, { waitUntil: "networkidle2" });

  const newsItems = await page.evaluate(() => {
    const items = [];
    // 뉴스 항목을 선택합니다.
    const newsElements = document.querySelectorAll(
      ".land_news_list .news_item"
    );
    newsElements.forEach((el) => {
      const title = el.querySelector(".title")
        ? el.querySelector(".title").innerText
        : null;
      const link = el.querySelector("a") ? el.querySelector("a").href : null;
      if (title && link) {
        items.push({ title, link });
      }
    });
    return items;
  });

  console.log(newsItems);
  await browser.close();
}

// 대상 URL을 함수에 전달합니다.
scrapeNewsHeadlines("https://land.naver.com/news/headline.naver");
