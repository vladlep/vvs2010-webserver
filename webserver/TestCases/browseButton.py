useFixture(default)

def test():
    java_recorded_version = '1.6.0_22'

    if window('Server GUI'):
        click('Browse')

        if window('Open'):
          
            select('FilePane$3', 'Desktop')
            click('Open')
        close()

        assert_p('TextField', 'Text', r'C:\Users\vll')
        assert_p('Label5', 'Text', 'No index.html')
        click('Start Server')

        if window('Message'):
            click('OK')
        close()

        click('Button2')
    close()
