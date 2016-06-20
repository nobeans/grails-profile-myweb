// 日付入力欄を初期化する。
(function () {
    $('.datepicker').each(function () {
        var $this = $(this);
        var dateFormat = $this.data('format').replace(/M/g, 'm');
        $this.pickadate({
            format: dateFormat || 'yyyy/mm/dd',
            monthsFull: [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月' ],
            monthsShort: [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月' ],
            weekdaysFull: [ '日曜日', '月曜日', '火曜日', '水曜日', '木曜日', '金曜日', '土曜日' ],
            weekdaysShort: [ '日', '月', '火', '水', '木', '金', '土' ],
            today: '今日',
            clear: '消去',
            close: '閉じる',
            firstDay: 1 // 月曜開始
        });
    });

    // カレンダアイコンもクリックできるようにする。
    $('.input-group-calendar').on('click', function (e) {
        e.stopPropagation();
        e.preventDefault();
        $(this).siblings('.datepicker').pickadate('open');
    });
})();
