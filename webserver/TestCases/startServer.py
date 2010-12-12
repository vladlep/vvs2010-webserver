useFixture(default)

def test():
    java_recorded_version = '1.6.0_22'

    if window('Server GUI'):
        click('MetalTitlePane', 271, 6)
        click('MetalTitlePane', 295, 11)
        click('MetalTitlePane', 130, 11)
        select('FormattedTextField', '127.000.000.001')
        select('FormattedTextField1', '80800')
        click('Browse')

        if window('Open'):
            select('FilePane$3','vll/Desktop')
            click('FilePane$3','Open')
        close()

        rightclick('Label5')
        rightclick('Label5')
        assert_p('Label5', 'Text', 'No index.html')
        assert_p('Label7', 'Text', 'Not set')
        click('Browse1')

        if window('Open'):
            select('FilePane$3','vll/Desktop')
            click('Open')
        close()

        assert_p('Label7', 'Text', 'No mentenace.html')
        assert_p('TextField', 'Text', r'C:\Users\vll\Desktop')
        assert_p('TextField1', 'Text', r'C:\Users\vll\Desktop')
        click('Start Server')

        if window('Message'):
            assert_p('OptionPane.body', 'Enabled', 'true')
            click('OK')
        close()

        select('TextField', r'C:\Users\vll\D')
        assert_p('Label5', 'Text', 'Not a directory')
        click('Browse')

        if window('Open'):
            select('FilePane$3', '')
            doubleclick('FilePane$3', '20')
            select('FilePane$3', 'Videos')
            select('FilePane$3', 'vlad')
            doubleclick('FilePane$3', '24')
            select('FilePane$3', 'scoala')
            doubleclick('FilePane$3', '3')
            select('FilePane$3', 'scoala')
            doubleclick('FilePane$3', '3')
            select('FilePane$3', 'test')
            click('Open')
        close()

        assert_p('Label5', 'Text', 'OK')
        assert_p('TextField', 'Text', r'C:\Users\vll\vlad\scoala\test')
        click('Start Server')
        assert_p('Start Server', 'Text', 'Stop Server')
        assert_p('FormattedTextField', 'Enabled', 'true')
        rightclick('FormattedTextField')
        assert_p('FormattedTextField', 'Text', '127.000.000.001')
        assert_p('FormattedTextField1', 'Text', '80800')
        rightclick('FormattedTextField1')
        assert_p('FormattedTextField1', 'Enabled', 'true')
        assert_p('TextField', 'Enabled', 'true')
        assert_p('Browse', 'Enabled', 'false')
        click('Button2')
    close()
